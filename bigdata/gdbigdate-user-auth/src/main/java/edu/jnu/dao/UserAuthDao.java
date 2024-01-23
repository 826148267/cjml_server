package edu.jnu.dao;

import edu.jnu.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 19时33分
 * @功能描述: 用户身份认证信息持久化接口
 */

public interface UserAuthDao extends JpaRepository<UserAuth, Integer> {
    UserAuth findByUserNameAndPassword(String userName, String password);
}
