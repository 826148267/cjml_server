package edu.jnu.service;

import com.aliyun.oss.model.OSSObject;
import edu.jnu.DTO.DataFileInfoDTO;
import edu.jnu.PO.DataFileInfoPO;
import edu.jnu.dao.DataFileInfoDAO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月15日 15:15
 */
@Transactional
@Service
public class DataFileService {

    @Autowired
    private DataFileInfoDAO dataFileInfoDAO;

    @Autowired
    private OSSService ossService;

    /**
     * 分页获取文件信息.
     * 根据用户id,当前页数，页容量获取指定页的文件信息列表.
     * @param pageOffset    当前页
     * @param size  页容量
     * @param userId 用户Id
     * @return  返回该页的文件信息列表
     */
    public DataFileInfoDTO listFileInfoByUserId(Integer pageOffset, Integer size, String userId) {
        PageRequest pageRequest = PageRequest.of(pageOffset, size);
        Page<DataFileInfoPO> dfips = dataFileInfoDAO.findAllByUserId(pageRequest, userId);
        return new DataFileInfoDTO(dfips.getTotalElements(), dfips.getContent());
    }

    /**
     * 根据fileId获取File文件输入流的byte[]
     * @param fileId    文件id
     * @return  返回文件数据输入流的byte[]形式
     */
    public byte[] getFileByFileId(String fileId) throws IOException {
        // 通过fileId查询出文件全路径
        DataFileInfoPO dataFileInfoPO = dataFileInfoDAO.findByFileId(fileId);
        String fileFullPath = "audit/data/"+dataFileInfoPO.getFilePath()+"/"+dataFileInfoPO.getFileName();
        // 根据文件全路径获取文件输入流
        OSSObject ossObject = ossService.getObj(fileFullPath, "gdbigdata");
        // 将输入流转化为byte[]
        byte[] results = IOUtils.toByteArray(ossObject.getObjectContent());
        ossObject.close();
        return results;
    }

    /**
     * 通过fileId获取文件的媒体类型
     * @param fileId 文件id
     * @return  文件的媒体类型
     */
    public String getMimeTypeByFileId(String fileId) {
        DataFileInfoPO dataFileInfoPO = dataFileInfoDAO.findByFileId(fileId);
        return dataFileInfoPO.getMimeType();
    }

    /**
     * 根据文件id删除OSS中的文件
     * @param fileId 文件id
     */
    public void deleteFileByFileId(String fileId) {
        // 通过fileId查询出文件全路径
        DataFileInfoPO dataFileInfoPO = dataFileInfoDAO.findByFileId(fileId);
        String fileFullPath = "audit/data/"+dataFileInfoPO.getFilePath()+"/"+dataFileInfoPO.getFileName();
        // 通过全路径删除文件
        ossService.deleteObj(fileFullPath, "gdbigdata");
    }

    /**
     * 根据文件id删除数据库中的文件信息
     * @param fileId 文件id
     */
    public void deleteFileInfoByFileId(String fileId) {
        dataFileInfoDAO.deleteByFileId(fileId);
    }
}
