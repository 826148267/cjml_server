package edu.jnu.dao;

import edu.jnu.PO.TagFileInfoPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月15日 11:19
 */
public interface TagFileInfoDAO extends JpaRepository<TagFileInfoPO, Integer> {

    /**
     * 根据fileId获取一条文件信息记录.
     * @param fileId  tag文件的文件id
     * @return  返回对应id的信息记录
     */
    TagFileInfoPO findByFileId(String fileId);

    /**
     * 根据fileId删除文件信息
     * @param fileId 文件id
     */
    void deleteByFileId(String fileId);
}
