package edu.jnu.service.thirdpart;

import edu.jnu.entity.CipherText;
import edu.jnu.entity.GetUserInfoPhase1DTO;
import edu.jnu.entity.GetUserInfoPhase2DTO;
import edu.jnu.entity.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年04月26日 19时23分
 * @功能描述: 用户信息操作的第三方接口
 */
@FeignClient(name = "UserInfo", url = "${access.real.server.url}")
public interface UserInfoService {
    @PostMapping("/rs/users")
    String addUser(UserDTO userDto);

    @PostMapping("/rs/osu-operation-first-phase")
    CipherText osuGetR(@RequestBody GetUserInfoPhase1DTO dto1);

    @PostMapping("/rs/osu-operation-second-phase")
    void osuUpdate(@RequestBody GetUserInfoPhase2DTO dto2);
}
