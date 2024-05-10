package edu.jnu.service;

import edu.jnu.PO.TagFileInfoPO;
import edu.jnu.dao.TagFileInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月15日 16:45
 */
@Transactional
@Service
public class TagFileService {

    @Autowired
    private TagFileInfoDAO tagFileInfoDAO;

    @Autowired
    private OSSService ossService;

    /**
     * 通过标签文件的文件id获取tag文件的全路径.
     * @param tagFileId 标签文件id
     * @return  返回标签文件的全路径
     */
    public String getTagFullPathByTagFileId(String tagFileId) {
        TagFileInfoPO tagFileInfoPO = tagFileInfoDAO.findByFileId(tagFileId);
        return tagFileInfoPO.getFilePath()+"/"+tagFileInfoPO.getFileName();
    }

    /**
     * 通过标签文件id获取R.
     * @param fileId  文件id
     * @return  返回R
     */
    public BigInteger getRByFileId(String fileId) {
        TagFileInfoPO tagFileInfoPO = tagFileInfoDAO.findByFileId(fileId);
        return new BigInteger(tagFileInfoPO.getR());
    }

    /**
     * 根据文件id删除OSS中的文件
     * @param fileId 文件id
     */
    public void deleteFileByFileId(String fileId) {
        // 通过fileId查询出文件全路径
        TagFileInfoPO tagFileInfoPO = tagFileInfoDAO.findByFileId(fileId);
        String fileFullPath = "audit/data/"+tagFileInfoPO.getFilePath()+"/"+tagFileInfoPO.getFileName();
        // 通过全路径删除文件
        ossService.deleteObj(fileFullPath, "gdbigdata");
    }

    /**
     * 根据文件id删除数据库中的文件信息
     * @param fileId 文件id
     */
    public void deleteFileInfoByFileId(String fileId) {
        tagFileInfoDAO.deleteByFileId(fileId);
    }
}
