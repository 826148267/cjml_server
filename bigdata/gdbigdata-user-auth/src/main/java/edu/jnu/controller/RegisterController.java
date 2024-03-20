package edu.jnu.controller;

import edu.jnu.entity.AuthDTO;
import edu.jnu.entity.RegisterVO;
import edu.jnu.entity.UserDTO;
import edu.jnu.service.thirdpart.AddUserService;
import edu.jnu.service.RegisterService;
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
 * @创建日期: 2023年05月01日 18时35分
 * @功能描述: 用户注册接口
 */
@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterVO registerVO) {
        // 将用户数据插入数据库
        UserDTO userDTO = UserDTO.builder()
                .userName(registerVO.getUserName())
                .userOrganization(registerVO.getUserOrganization())
                .userAddress(registerVO.getUserAddress())
                .userFileNums(registerVO.getUserFileNums())
                .build();
        if (!registerService.addUserInfo(userDTO)) {
            return ResponseEntity.badRequest().body("注册失败,可能是用户名已存在");
        }
        // 将用户名和密码写入身份认证表
        AuthDTO authDTO = AuthDTO.builder()
                .userName(registerVO.getUserName())
                .password(registerVO.getPassword())
                .build();
        if (registerService.add(authDTO)) {
            return ResponseEntity.ok("注册成功");
        } else {
            return ResponseEntity.badRequest().body("注册失败，可能是网络抖动");
        }
    }
}
