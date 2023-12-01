package edu.jnu.dao;

import edu.jnu.PO.DataFileInfoPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月15日 11:14
 */
public interface DataFileInfoDAO extends JpaRepository<DataFileInfoPO, Integer> {
    /**
     * 检索所有属于该userId的文件信息记录.
     * @param pageable   分页对象
     * @param userId    用户id
     * @return 返回该页文件信息记录
     */
    Page<DataFileInfoPO> findAllByUserId(Pageable pageable, String userId);

    /**
     * 根据fileId获取对应的文件信息
     * @param fileId    文件id
     * @return  返回整条文件记录
     */
    DataFileInfoPO findByFileId(String fileId);

    /**
     * 根据fileId删除文件信息
     * @param fileId 文件id
     */
    void deleteByFileId(String fileId);
}
