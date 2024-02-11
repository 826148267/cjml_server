package edu.jnu.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.model.OSSObject;
import edu.jnu.dao.DataFileDAO;
import edu.jnu.entity.FileListDTO;
import edu.jnu.entity.TransParams;
import edu.jnu.entity.po.DataFilePO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月06日 12时30分
 * @功能描述: 提供文件服务
 */
@Service
@Slf4j
public class FileService {

    @Autowired
    private DataFileDAO dataFileDAO;

    @Autowired
    private OSSService ossService;


    public boolean existFile(String fileAbstract, String mimeType) {
        if (dataFileDAO.existsByFileAbstractAndMimeType(fileAbstract, mimeType)) {
            return true;
        }
        return false;
    }

    public TransParams addDataFileInfo(String userName, String fileName, String mimeType, String fileAbstract) {
        // 获取第一个存储该文件的用户
        Optional<DataFilePO> firstSaver = dataFileDAO.findFirstSaver(fileAbstract, mimeType, 1);
        String thisFileId = UUID.randomUUID().toString();
        DataFilePO dataFilePO = DataFilePO.builder()
                                            .fileId(thisFileId)
                                            .userName(userName)
                                            .fileName(fileName)
                                            .mimeType(mimeType)
                                            .fileAbstract(fileAbstract)
                                            .firstSaveFlag(0)
                                            .fileActualId(firstSaver.get().getFileId())
                                            .build();
        dataFileDAO.save(dataFilePO);
        String string = null;
        try {
            string = ossService.getStringInOssObject(firstSaver.get().getTransParamsFileId(), "gdbigdata");
        } catch (IOException e) {
            System.out.println("读取文件失败："+e.getMessage());
        }
        return JSON.parseObject(string, TransParams.class);
    }

    public String saveDataFile(String data) {
        String fileId = UUID.randomUUID().toString();
        ossService.uploadObj2OSS(fileId, "gdbigdata", IOUtils.toInputStream(data, StandardCharsets.UTF_8));
        log.info("数据文件存储成功");
        return fileId;
    }

    public String saveAuditParamsFile(String auditParams) {
        String fileId = UUID.randomUUID().toString();
        ossService.uploadObj2OSS(fileId, "gdbigdata", IOUtils.toInputStream(auditParams, StandardCharsets.UTF_8));
        log.info("审计材料文件存储成功");
        return fileId;
    }

    public String saveTransParamsFile(String transParams) {
        String fileId = UUID.randomUUID().toString();
        ossService.uploadObj2OSS(fileId, "gdbigdata", IOUtils.toInputStream(transParams, StandardCharsets.UTF_8));
        log.info("所有权转移文件存储成功");
        return fileId;
    }

    public void saveDataFileInfo(String fileId, String userName, String fileName, String mimeType, String fileAbstract) {
        // 获取第一个存储该文件的用户
        DataFilePO dataFilePO = DataFilePO.builder()
                .fileId(fileId)
                .userName(userName)
                .fileName(fileName)
                .mimeType(mimeType)
                .fileAbstract(fileAbstract)
                .fileActualId(fileId)
                .firstSaveFlag(1)
                .build();
        dataFileDAO.save(dataFilePO);
    }

    public String saveSignFile(String signs) {
        String fileId = UUID.randomUUID().toString();
        ossService.uploadObj2OSS(fileId, "gdbigdata", IOUtils.toInputStream(signs, StandardCharsets.UTF_8));
        log.info("签名文件存储成功");
        return fileId;
    }

    public void perfectFileInfo(String userName, String fileName, String signFileId, String transParamsFileId, String auditParamsFileId) {
        dataFileDAO.updateAuditParamsFileIdAndTransParamsFileIdAndTagFileIdByUserNameAndFileName(auditParamsFileId,
                transParamsFileId, signFileId, userName, fileName);
    }

    public String getSignFileIdByUserNameAndFileName(String userName, String fileName) {
        Optional<DataFilePO> poOptional = dataFileDAO.findByUserNameAndFileName(userName, fileName);
        String fileAbstract = poOptional.get().getFileAbstract();
        String mimeType = poOptional.get().getMimeType();
        Optional<DataFilePO> firstSaver = dataFileDAO.findFirstSaver(fileAbstract, mimeType, 1);
        if (firstSaver.isPresent()) {
            return firstSaver.get().getTagFileId();
        }
        return "";
    }

    /**
     * 判断文件是不是被用户自身存储过了
     * @param fileAbstract 文件内容
     * @param mimeType 文件类型
     * @param userName 用户名
     * @param fileName 文件名
     * @return true 表示存在，false 表示不存在
     */
    public boolean isSaveDupBySelf(String fileAbstract, String mimeType, String userName, String fileName) {
        return dataFileDAO.existsByUserNameAndFileNameAndFileAbstractAndMimeType(userName, fileName, fileAbstract, mimeType);
    }

    /**
     * 获取文件列表
     * @param userName 用户名
     * @return 文件列表
     */
    public List<FileListDTO> listFiles(String userName) {
        List<DataFilePO> dataFilePOList = dataFileDAO.findByUserName(userName);
        return dataFilePOList.stream()
                .parallel()
                .map(dataFilePO -> FileListDTO.builder()
                        .fileId(dataFilePO.getFileId())
                        .fileName(dataFilePO.getFileName())
                        .isFirstSaver(dataFilePO.getFirstSaveFlag().toString())
                        .signFileId(dataFilePO.getTagFileId())
                        .auditParamsFileId(dataFilePO.getAuditParamsFileId())
                        .build())
                .peek(fileListDTO -> {
                    if ("1".equals(fileListDTO.getIsFirstSaver())) {
                        fileListDTO.setIsFirstSaver("是");
                    } else {
                        fileListDTO.setIsFirstSaver("否");
                    }
                })
                .toList();
    }

    public String getFileAsString(String fileId) {
        Optional<DataFilePO> filePO = dataFileDAO.findByFileId(fileId);
        String fileActualId = filePO.map(DataFilePO::getFileActualId).orElse(null);
        String string = null;
        try {
            string = ossService.getStringInOssObject(fileActualId, "gdbigdata");
        } catch (IOException e) {
            System.out.println("从OSS中读取文件内容失败"+e.getMessage());
        }
        return string;
    }

    public String getMimeTypeByFileId(String fileId) {
        Optional<DataFilePO> filePO = dataFileDAO.findByFileId(fileId);
        return filePO.map(DataFilePO::getMimeType).orElse("application/pdf");
    }

    /**
     * 根据文件的路径获取文件的审计参数
     * @param fileId 文件的ID
     * @return 文件的审计参数
     */
    public String getAuditParams(String fileId) {
        Optional<DataFilePO> filePO = dataFileDAO.findByFileId(fileId);
        String auditParamsFileId = filePO.map(DataFilePO::getAuditParamsFileId).orElse(null);
        try {
            return ossService.getStringInOssObject(auditParamsFileId, "gdbigdata");
        } catch (IOException e) {
            System.out.println("从OSS存储中获取审计参数失败:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过行数数组从输入流中获取数据块集合.
     * @param inputStream 输入流
     * @param iList 列表中为数字集合，数字代表需要读取的行数
     * @return  返回数据块集合
     */
    public ArrayList<String> getListFromPointedLinesArrayInInputStream(InputStream inputStream, ArrayList<Integer> iList) {
        ArrayList<String> result = new ArrayList<>();   // 函数返回值
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String str = null;  // 当前行数据块
        int lineCounter = 0;   // 当前行指针
        try {
            // 初始化目标行号
            if (iList.size() < 1) {
                log.info("随机质询下标集合为空，无法产生证明");
                return null;
            }
            int index = 0;
            int lineNum = iList.get(index);

            while ((str = reader.readLine()) != null) { // 读取下一行
                // 如果目标行号等于当前行号
                if (((int) lineNum) == lineCounter) {
                    // 则取出该行数据
                    result.add(str);
                    // 更新到下一个目标行号,
                    index ++;
                    if (index < iList.size()) {
                        lineNum = iList.get(index);
                    } else {    // 如果时最后一个目标行号，就结束读取输入流
                        break;
                    }
                }
                lineCounter ++; // 当前行指针后移1行
            }
        } catch (IOException e) {
            log.error("读取输入流时失败，原因为{}", e.getMessage());
        } finally {
            try {
                inputStreamReader.close();
                reader.close();
            } catch (IOException e) {
                log.error("关闭输入流时失败，原因为{}", e.getMessage());
            }
        }
        return result;
    }


    /**
     * 通过文件id获取签名文件的id
     * @param fileId 文件id
     * @return  签名文件的id
     */
    public String getTagFileId(String fileId) {
        Optional<DataFilePO> filePO = dataFileDAO.findByFileId(fileId);
        return filePO.map(DataFilePO::getTagFileId).orElse(null);
    }

    /**
     * 通过fileId获取文件实际存储时的ID
     * @param fileId 文件id
     * @return 文件实际存储时的ID
     */
    public String getDataFileId(String fileId) {
        Optional<DataFilePO> filePO = dataFileDAO.findByFileId(fileId);
        return filePO.map(DataFilePO::getFileActualId).orElse(null);
    }
}
