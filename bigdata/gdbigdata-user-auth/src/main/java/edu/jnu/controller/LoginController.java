package edu.jnu.controller;

import edu.jnu.entity.LoginVO;
import edu.jnu.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 20时56分
 * @功能描述: 用户登陆接口
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginVO loginVO) {
        String password = loginVO.getPassword();
        String userName = loginVO.getUserName();
        if (!loginService.checkPassword(userName, password)) {
            log.info("用户名或密码错误");
            return ResponseEntity.badRequest().body("用户名或密码错误");
        }
        log.info("用户名或密码正确");
        return ResponseEntity.ok("登陆成功");
    }
}
