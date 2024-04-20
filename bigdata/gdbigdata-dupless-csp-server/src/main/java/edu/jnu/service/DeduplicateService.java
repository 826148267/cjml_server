package edu.jnu.service;

import com.aliyun.oss.model.OSSObject;
import edu.jnu.dao.DataFileInfoDao;
import edu.jnu.dao.KeyFileInfoDao;
import edu.jnu.domain.FileInfo;
import edu.jnu.po.DataFileInfoPo;
import edu.jnu.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 去重服务类.
 * 专门进行去重.
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月15日 21:31
 */
@Transactional
@Service
public class DeduplicateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeduplicateService.class);

    @Autowired
    private DataFileInfoDao dataFileInfoDao;

    @Autowired
    private KeyFileInfoDao keyFileInfoDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private OSSService ossService;

    /**
     * 对文件进行存储并去重.
     * @param fileInfo  待存储文件信息
     * @return  如果需要对文件进行存储就返回false,如果不需要对文件进行存储就返回true
     */
    public boolean saveWithDeduplicate(FileInfo fileInfo) {
        // 判断table_File_Info表中是否有已存文件的hash值和当前文件内容的hash值相同
        if (primaryJudgeFileIsExist(fileInfo.getDataHashValue())) {    // 如果存在，进一步对比文件内容
            // 索引出文件列表
            List<DataFileInfoPo> fips = dataFileInfoDao.findByDataHashValueIsAndDeduplicateFlagIs(fileInfo.getDataHashValue(), 1);
            boolean existSameFile = false;
            // 遍历文件列表，逐个读取文件
            for (DataFileInfoPo fip : fips) {
                OSSObject oo = ossService.getObj(fip.getFileActualPath()+">>"+fip.getFileActualName(), "gdbigdata");
                // 判断两文件的数据
                try {
                    if (Tools.isSameContent(oo.getObjectContent(), fileInfo.getFile().getInputStream())) {
                        existSameFile = true;
                        // 如果两个文件的数据还是一样，就将当前文件的实际存储字段改成已存在文件的实际存储字段的值
                        DataFileInfoPo dataFileInfoPo = new DataFileInfoPo(fileInfo);
                        dataFileInfoPo.setFileActualName(fip.getFileActualName());
                        dataFileInfoPo.setFileActualPath(fip.getFileActualPath());
                        dataFileInfoDao.save(dataFileInfoPo);
                    }
                    oo.close();
                } catch (IOException e) {
                    LOGGER.debug("读取文件失败，可能原因是:\n1、fileInfo对象的输入流错误\n2、OSS检索出的文件对象的输入流有问题");
                    LOGGER.error("错误原因:"+ Arrays.toString(e.getStackTrace()));
                }
                break;
            }
            // 如果所有文件的数据不一样，就进行简单存储文件,如果存储成功就返回true
            if (!existSameFile) {
                boolean simpleSaveStatus = fileService.saveFileSimple(fileInfo);
                if (simpleSaveStatus) {
                    LOGGER.debug("hash碰撞了，已直接存储");
                    return true;
                } else {
                    LOGGER.debug("hash碰撞了，且直接存储时发生了错误");
                    return false;
                }
            }
            LOGGER.debug("已进行去重存储");
            return true;
        } else {    // 如果不存在，直接进行简单存储
            LOGGER.debug("无已存在文件的hash值相同，将直接存储");
            return fileService.saveFileSimple(fileInfo);
        }
    }

    /**
     * 初步判断table_File_Info表中是否有hash值和当前文件内容的hash值相同.
     * @param hashValue 待存储文件hash值
     * @return  返回布尔值:1、如果存在返回true 2、如果不存在返回false
     */
    private boolean primaryJudgeFileIsExist(String hashValue) {
        return dataFileInfoDao.existsByDataHashValue(hashValue);
    }

    /**
     * 进行全局去重.
     * 相当于将存储时未去重的文件重新进行一次去重时存储操作（可能存在更好的算法，but先跑起来再说）.
     * @return 返回全局去重操作执行状态,1、如果去重成功返回true 2、去重失败返回false
     */
    public boolean deduplicationGlobal() {
        // 1、分页索引未去重的记录
        // 2、将这些记录进行去重时存储
        int pageOffset = 0;    // 当前页面，初始页面从0开始
        int size = 100; // 每页记录数
        int totalPages = 0; // 页面总数
        while (pageOffset <= totalPages) {  // 如果当前页数不大于总页数
            PageRequest pageRequest = PageRequest.of(pageOffset, size);
            Page<DataFileInfoPo> fips = dataFileInfoDao.findByDeduplicateFlagIs(pageRequest, 0);
            fips.forEach(dataFileInfoPo -> {
                // 将记录进行去重时存储的操作
                if (!reSaveWithDeduplicate(dataFileInfoPo)) {
                    LOGGER.warn("文件id:"+ dataFileInfoPo.getFileId()+"重新去重存储失败");
                }
            });
            totalPages = fips.getTotalPages();  // 更新总页数
            ++ pageOffset;  // 当前页数后移
        }
        return true;
    }

    /**
     * 将未去重的文件重新进行去重存储.
     * @param dataFileInfoPo  文件信息对象
     * @return  返回该文件重新去重存储操作执行状态，1、重新存储成功则返回true 2、重新存储失败返回false
     */
    private boolean reSaveWithDeduplicate(DataFileInfoPo dataFileInfoPo) {
        // 1、判断现有的表中是否含有相同hash值的文件
        // 2、如果有，就对比两个文件是否一样，
        //      如果文件也一样，就把修改真实存储路径和真实文件名，并把去重标记置为1
        // 3、如果没有，直接将去重标志置为1
        if (!primaryJudgeFileIsExist(dataFileInfoPo.getDataHashValue())) {
            dataFileInfoPo.setDeduplicateFlag(1);
            dataFileInfoDao.save(dataFileInfoPo);
            LOGGER.info("文件id:"+ dataFileInfoPo.getFileId()+"重新去重存储成功");
        } else {
            List<DataFileInfoPo> fips = dataFileInfoDao.findByDataHashValueIsAndDeduplicateFlagIs(dataFileInfoPo.getDataHashValue(), 1);
            boolean existSameFile = false;
            OSSObject ooBase = ossService.getObj(dataFileInfoPo.getFileActualPath()+">>"+dataFileInfoPo.getFileActualName(), "gdbigdata");
            // 遍历文件列表，逐个读取文件
            for (DataFileInfoPo fip : fips) {
                OSSObject oo = ossService.getObj(fip.getFileActualPath()+">>"+fip.getFileActualName(), "gdbigdata");
                // 判断两文件的数据
                try {
                    if (Tools.isSameContent(oo.getObjectContent(), ooBase.getObjectContent())) {
                        existSameFile = true;
                        // 如果两个文件的数据还是一样
                        // 1、将当前文件删除，
                        ossService.deleteOssObject(dataFileInfoPo.getFileActualPath()+">>"+dataFileInfoPo.getFileActualName(), "gdbigdata");
                        // 2、当前文件的实际存储字段改成已存在文件的实际存储字段的值
                        dataFileInfoPo.setFileActualName(fip.getFileActualName());
                        dataFileInfoPo.setFileActualPath(fip.getFileActualPath());
                        dataFileInfoPo.setDeduplicateFlag(1);
                        dataFileInfoDao.save(dataFileInfoPo);
                    }
                    oo.close();
                } catch (IOException e) {
                    LOGGER.debug("读取文件失败，可能原因是:\n1、fileInfo对象的输入流错误\n2、OSS检索出的文件对象的输入流有问题");
                    LOGGER.error("错误原因:"+ Arrays.toString(e.getStackTrace()));
                }
                break;
            }
            // 如果所有文件的数据不一样，就进行简单存储文件,如果存储成功就返回true
            if (!existSameFile) {
                // 进行转储
                dataFileInfoPo.setDeduplicateFlag(1);
                dataFileInfoDao.save(dataFileInfoPo);
            }
            LOGGER.info("文件id:"+ dataFileInfoPo.getFileId()+"重新去重存储成功");
            return true;
        }
        return true;
    }

    /**
     * 保存密钥文件.
     * @param userId 用户标识符userid
     * @param storageType   存储介质，如果OSS，HDFS等
     * @param file  文件
     * @return  返回操作执行状态：1、保存成功返回true；2、保存失败返回false
     */
    public boolean saveKeyFile(String userId, Integer storageType , MultipartFile file) {
        return fileService.saveFileSimple(userId, storageType, file);
    }

    /**
     * 通过文件id删除数据文件信息.
     * @param fileId 数据文件Id
     */
    public void deleteDataFileInfoByFileId(String fileId) {
        dataFileInfoDao.deleteByFileId(fileId);
    }

    /**
     * 通过密钥文件全路径删除密钥文件.
     * @param fileFullPath  密钥文件全路径
     */
    public void deleteKeyFileByFileFullPath(String fileFullPath) {
        ossService.deleteOssObject(fileFullPath, "gdbigdata");
    }

    /**
     * 通过逻辑路径和逻辑文件名匹配出密钥文件信息，并删除.
     * @param filePath 密钥文件逻辑路径
     * @param fileName 密钥文件逻辑名
     */
    public void deleteKayFileInfoByFilePathAndFileName(String filePath, String fileName) {
        keyFileInfoDao.deleteByFilePathIsAndFileNameIs(filePath, fileName);
    }
}
