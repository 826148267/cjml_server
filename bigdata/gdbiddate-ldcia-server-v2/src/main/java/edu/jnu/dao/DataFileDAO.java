package edu.jnu.dao;

import edu.jnu.entity.po.DataFilePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月06日 13时24分
 * @功能描述: TODO
 */

public interface DataFileDAO extends JpaRepository<DataFilePO, Integer> {
    Optional<DataFilePO> findByFileId(String fileId);
    boolean existsByUserNameAndFileNameAndFileAbstractAndMimeType(String userName, String fileName, String fileAbstract, String mimeType);


    @Transactional
    @Modifying
    @Query("""
            update table_data_file t set t.auditParamsFileId = ?1, t.transParamsFileId = ?2, t.tagFileId = ?3
            where t.userName = ?4 and t.fileName = ?5""")
    void updateAuditParamsFileIdAndTransParamsFileIdAndTagFileIdByUserNameAndFileName(String auditParamsFileId, String transParamsFileId, String tagFileId, String userName, String fileName);
    @Query("select t from table_data_file t where t.userName = ?1 and t.fileName = ?2")
    Optional<DataFilePO> findByUserNameAndFileName(String userName, String fileName);
    @Transactional
    @Modifying
    @Query("""
            update table_data_file t set t.tagFileId = ?1, t.transParamsFileId = ?2
            where t.userName = ?3 and t.fileName = ?4""")
    void perfectFileInfo(String tagFileId, String transParamsFileId, String userName, String fileName);
    @Query("select t from table_data_file t where t.fileAbstract = ?1 and t.mimeType = ?2 and t.firstSaveFlag = ?3")
    Optional<DataFilePO> findFirstSaver(String fileAbstract, String mimeType, Integer firstSaveFlag);
    boolean existsByFileAbstractAndMimeType(String fileAbstract, String mimeType);

    List<DataFilePO> findByUserName(String userName);
}
