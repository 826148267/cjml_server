package edu.jnu.service;

import com.aliyun.oss.model.OSSObject;
import edu.jnu.constant.StorageType;
import edu.jnu.dao.DataFileInfoDao;
import edu.jnu.dao.KeyFileInfoDao;
import edu.jnu.domain.FileInfo;
import edu.jnu.dto.GetUserFileListDto;
import edu.jnu.exception.UnknownStorageTypeException;
import edu.jnu.po.DataFileInfoPo;
import edu.jnu.po.KeyFileInfoPo;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月15日 21:30
 */
@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private OSSService ossService;

    @Autowired
    private DataFileInfoDao dataFileInfoDao;

    @Autowired
    private KeyFileInfoDao keyFileInfoDao;

    /**
     * 通过存储类型将文件存储到对应的介质内,
     * 简单保存文件，不做任何额外处理.
     *
     * @return 返回存储状态布尔值：true为存储成功，false为存储失败
     */
    public boolean saveFileSimple(FileInfo fileInfo) {
        /*
        第1步：存储到介质中
        第2步：如果第1步存储成功，就存入表中
        */
        boolean operationStatus = false;    // 当前操作状态
        DataFileInfoPo dataFileInfoPo = new DataFileInfoPo(fileInfo);
        if (fileInfo.getStorageType() == StorageType.OSS.getCode()) {   // 如果是存储到OSS中
            operationStatus = saveFileInOss(dataFileInfoPo.getFileActualPath() + ">>" + dataFileInfoPo.getFileActualName(), fileInfo.getFile());
        } else {
            LOGGER.debug("不支持的存储介质,用户想要存储的类型代码为" + fileInfo.getStorageType());
            throw new UnknownStorageTypeException("不支持的存储介质");
        }
        if (operationStatus) {  // 如果保存到存储介质成功
            // 将文件信息存入数据库表中
            DataFileInfoPo result = dataFileInfoDao.save(dataFileInfoPo);
            if (result.getId() <= 0) operationStatus = false;
        }
        return operationStatus;
    }

    /**
     * 通过存储类型将文件存储到对应的介质内,
     * 简单保存文件，不做任何额外处理.
     *
     * @return 返回存储状态布尔值：true为存储成功，false为存储失败
     */
    public boolean saveFileSimple(String userId, Integer storageType, MultipartFile file) {
        /*
        第1步：存储到介质中
        第2步：如果第1步存储成功，就存入表中
        */
        boolean operationStatus = false;    // 当前操作状态
        KeyFileInfoPo keyFileInfoPo = new KeyFileInfoPo(userId, file);
        if (storageType == StorageType.OSS.getCode()) {   // 如果是存储到OSS中
            operationStatus = saveFileInOss(file.getOriginalFilename(), file);
        } else {
            LOGGER.debug("不支持的存储介质,用户想要存储的类型代码为" + storageType);
            throw new UnknownStorageTypeException("不支持的存储介质");
        }
        if (operationStatus) {  // 如果保存到存储介质成功
            // 将文件信息存入数据库表中
            KeyFileInfoPo result = keyFileInfoDao.save(keyFileInfoPo);
            if (result.getId() <= 0) operationStatus = false;
        }
        return operationStatus;
    }

    /**
     * 将文件存储到OSS中.
     * @param fileFullName 文件全路径名
     * @param file         待存储文件对象
     * @return 返回存储操作状态:true为存储成功，false为存储失败
     */
    private boolean saveFileInOss(String fileFullName, MultipartFile file) {
        String bucketName = "gdbigdata";    // 存储到gdbigdata这个bucketName中
        try {
            ossService.uploadObjAsPlainTxt2OSS(fileFullName, bucketName, file.getInputStream());
            return true;
        } catch (IOException e) {
            LOGGER.debug("存储文件失败，无法获取文件的输入流，请检查MultipartFile对象是否正确读取");
            return false;
        }
    }

    /**
     * 根据用户Id分页获取该用户的文件信息列表
     *
     * @param userId      用户Id
     * @param currentPage 当前页数
     * @param size        页容量
     * @return 返回文件信息列表分页查询结果
     */
    public GetUserFileListDto getFileListByUserId(String userId, Integer currentPage, Integer size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        Page<DataFileInfoPo> pageInfo = dataFileInfoDao.findAllByUserId(userId, pageable);
        GetUserFileListDto result = new GetUserFileListDto();
        List<edu.jnu.dto.FileInfo> fileInfoList = new ArrayList<>();
        for (int i = 0; i < pageInfo.getContent().size(); i++) {
            edu.jnu.dto.FileInfo fileInfo = new edu.jnu.dto.FileInfo(pageInfo.getContent().get(i));
            fileInfoList.add(fileInfo);
        }
        result.setFileInfoList(fileInfoList);
        result.setRecordTotals(pageInfo.getTotalElements());
        return result;
    }

    /**
     * 根据给定的fileId下载文件.
     * @param fileId 文件唯一标识符id
     * @return 返回待下载的文件的byte[]数组对象
     */
    public byte[] downloadFileByFileIdInOss(String fileId) throws IOException {
        DataFileInfoPo dataFileInfoPo = dataFileInfoDao.findByFileId(fileId);
        String fileFullName = dataFileInfoPo.getFileActualPath() + ">>" + dataFileInfoPo.getFileActualName();
        OSSObject ossObject = ossService.getObj(fileFullName, "gdbigdata");
        byte[] results = IOUtils.toByteArray(ossObject.getObjectContent());
        ossObject.close();
        return results;
    }

    /**
     * 根据数据文件的fileId来获取密钥文件的正文.
     * @param dataFileId    数据文件的fileId
     * @return  返回byte数组形式的密钥文件正文
     */
    public byte[] getKeyFileByDataFileId(String dataFileId) throws IOException {
        // 获取dataFileId对应的记录
        DataFileInfoPo dataFileInfoPo = dataFileInfoDao.findByFileId(dataFileId);
        String targetFilePath = dataFileInfoPo.getFileLogicPath();
        String targetFileName = dataFileInfoPo.getFileLogicName().split("\\.")[0]+".key";
        // 根据文件路径和文件名获取密钥文件
        String fileFullName = targetFilePath + ">>" + targetFileName;
        OSSObject ossObject = ossService.getObj(fileFullName, "gdbigdata");
        byte[] results = IOUtils.toByteArray(ossObject.getObjectContent());
        ossObject.close();
        return results;
    }
}
