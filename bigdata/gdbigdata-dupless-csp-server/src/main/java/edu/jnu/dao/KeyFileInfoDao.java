package edu.jnu.dao;

import edu.jnu.po.KeyFileInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月10日 20:41
 */
public interface KeyFileInfoDao extends JpaRepository<KeyFileInfoPo, Integer> {
    /**
     * 匹配出文件逻辑路径=fileLogicPath，文件逻辑名=fileLogicName的密钥文件的记录，并删除.
     * @param fileLogicPath 密钥文件逻辑路径
     * @param fileLogicName 密钥文件逻辑名
     */
    void deleteByFilePathIsAndFileNameIs(String fileLogicPath, String fileLogicName);
}
