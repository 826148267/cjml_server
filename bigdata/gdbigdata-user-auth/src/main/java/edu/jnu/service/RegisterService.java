package edu.jnu.service;

import edu.jnu.dao.UserAuthDao;
import edu.jnu.entity.AuthDTO;
import edu.jnu.entity.UserAuth;
import edu.jnu.entity.UserDTO;
import edu.jnu.service.thirdpart.AddUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 18时56分
 * @功能描述: 用户注册服务类
 */
@Service
@Slf4j
public class RegisterService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AddUserService addUserService;

    public boolean isExist(String userName) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(userName));
    }

    /**
     * 插入用户身份认证数据
     * @param authDTO 用户身份认证数据
     * @return 是否成功
     */
    public boolean add(AuthDTO authDTO) {
        UserAuth userAuth = UserAuth.builder()
                .userName(authDTO.getUserName())
                .password(authDTO.getPassword())
                .build();
        UserAuth auth = userAuthDao.save(userAuth);
        if (auth.getId() == null) {
            log.error("注册用户失败,原因是：身份认证数据插入失败");
            return false;
        } else {
            log.info("注册用户成功,用户名:{}", auth.getUserName());
            return true;
        }
    }

    public boolean addUserInfo(UserDTO userDTO) {
        // 判断redis中是否存在userName，存在说明已经被注册。
        String userName = userDTO.getUserName();
        if (isExist(userName)) {
            return false;
        }
        // 插入用户数据
        try {
            addUserService.addUser(userDTO, userName);
        } catch (Exception e) {
            log.error("添加用户信息失败,原因是：{}", e.getMessage());
            return false;
        }
        return true;
    }
}
