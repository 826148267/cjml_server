package edu.jnu.service;

import edu.jnu.dao.DataFileInfoDAO;
import edu.jnu.dao.FilePositionDao;
import edu.jnu.PO.DataFileInfoPO;
import edu.jnu.PO.TagFileInfoPO;
import edu.jnu.dao.TagFileInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月04日 19:40
 */
@Service
public class FilePositionService {

    @Autowired
    private DataFileInfoDAO dataFileInfoDAO;

    @Autowired
    private TagFileInfoDAO tagFileInfoDAO;

    /**
     * 记录数据文件和标签文件的存储位置
     * @param userId 用户id
     * @param fileStoragePath   文件所在目录
     * @param originalDataFilename  上传时的数据文件名
     * @param originalTagFilename   上传时的标签文件名
     * @param blockNum  数据块的块数
     */
    public void recordDataAndTagFileInfo(String userId,
                                          String fileStoragePath,
                                          String originalDataFilename,
                                          String originalTagFilename,
                                          Integer blockNum,
                                          String R,
                                          String mimeType) {
        // 获取数据文件的fileId
        String dataFileId = UUID.randomUUID().toString();
        // 获取标签文件的fileId
        String tagFileId = UUID.randomUUID().toString();
        DataFileInfoPO dfi = new DataFileInfoPO(
                dataFileId,
                fileStoragePath,
                originalDataFilename,
                blockNum,
                tagFileId,
                userId,
                mimeType
        );
        TagFileInfoPO tfi = new TagFileInfoPO(
                tagFileId,
                fileStoragePath,
                originalTagFilename,
                R
        );
        dataFileInfoDAO.save(dfi);
        tagFileInfoDAO.save(tfi);
    }

    /**
     * 分页查询文件位置
     * @param pageOffset    当前页数
     * @param size  页大小
     * @return  文件路径列表
     */
//    public List<DataFileInfoPO> getAllFilePosition(Integer pageOffset, Integer size) {
//        PageRequest pageRequest = PageRequest.of(pageOffset, size);
//        Page<DataFileInfoPO> fps = filePositionDao.findAll(pageRequest);
//        return fps.stream().filter(element -> {
//            element.setDataFilePath(element.getDataFilePath().split("data/")[1]);
//            element.setTagFilePath(element.getTagFilePath().split("tag/")[1]);
//            return true;
//        }).toList();
//    }

    /**
     * 统计表中记录数.
     * @return 返回总记录数
     */
//    public Integer getTotals() {
//        return Math.toIntExact(filePositionDao.count());
//    }
}
