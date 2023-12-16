package edu.jnu.service.thirdpart;

import edu.jnu.entity.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 19时04分
 * @功能描述: 调用隐私信息访问的添加用户的接口
 */
@FeignClient(name = "User", url = "${access.middle.server.url}")
public interface AddUserService {
    @PostMapping("/access/{userName}")
    void addUser(@RequestBody UserDTO userDto, @PathVariable("userName") String userName);
}
