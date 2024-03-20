package edu.jnu.service;

import edu.jnu.dao.UserAuthDao;
import edu.jnu.entity.UserAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 20时59分
 * @功能描述: 登陆功能业务类
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private UserAuthDao userAuthDao;

    public boolean checkPassword(String userName, String password) {
        UserAuth userAuth = userAuthDao.findByUserNameAndPassword(userName, password);
        return userAuth != null;
    }
}
