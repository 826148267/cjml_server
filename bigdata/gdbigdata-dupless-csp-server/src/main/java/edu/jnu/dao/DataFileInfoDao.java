package edu.jnu.dao;

import edu.jnu.po.DataFileInfoPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月16日 13:01
 */
public interface DataFileInfoDao extends JpaRepository<DataFileInfoPo, Integer> {

    /**
     * 根据hash筛选出表中hash值一样的文件信息列表(所有文件).
     * @param hashValue 文件内容的hash值
     * @return  返回相同hash值的文件信息列表
     */
    List<DataFileInfoPo> findByDataHashValueIs(String hashValue);

    /**
     * 根据hash判断出表中是否含有hash值一样的已存文件.
     * @param hashValue 待存储文件
     * @return  返回布尔值:1、存在返回true，2、不存在返回false
     */
    boolean existsByDataHashValue(String hashValue);


    /**
     * 分页索引出未去重的记录.
     * @param pageable 分页接口
     * @param deduplicateFlag 去重标志位
     * @return  返回目标页的为未去重记录
     */
    Page<DataFileInfoPo> findByDeduplicateFlagIs(Pageable pageable, int deduplicateFlag);

    /**
     * 根据hash筛选出表中hash值一样的文件信息列表(仅已去重文件).
     * @param dataHashValue 文件内容的hash值
     * @param deduplicateFlag 去重标志
     * @return 返回文件信息列表
     */
    List<DataFileInfoPo> findByDataHashValueIsAndDeduplicateFlagIs(String dataHashValue, Integer deduplicateFlag);

    /**
     * 分页查询表中userId为给定用户Id的所有记录.
     * @param userId    用户Id
     * @param pageable  分页对象
     * @return  分页查询结果
     */
    Page<DataFileInfoPo> findAllByUserId(String userId, Pageable pageable);

    /**
     * 根据文件id获取文件信息.
     * @param fileId 文件唯一标识符Id
     * @return 返回该文件id对应的文件信息
     */
    DataFileInfoPo findByFileId(String fileId);

    /**
     * 通过fileId删除其对应行
     * @param fileId    文件唯一标识符
     */
    void deleteByFileId(String fileId);
}
