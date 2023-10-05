package edu.jnu.controller;

import com.alibaba.fastjson.JSONObject;
import edu.jnu.entity.*;
import edu.jnu.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年04月25日 17时34分
 * @功能描述: 用户信息操作
 */
@RestController
@RequestMapping("/access")
@Api(tags = "用户信息操作接口")
@Slf4j
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/{userName}")
    public ResponseEntity<String> addUser(@Validated @RequestBody AddUserVO addUserVO, @PathVariable("userName") String userName) {
        if (userService.isExist(userName)) {
            log.info("用户名："+userName+"---试图重复创建用户资料，已拒绝创建");
            return ResponseEntity.accepted().body("用户名："+userName + "---试图重复创建用户资料");
        }

        UserDTO userDTO = UserDTO.builder()
                .userName(userName)
                .userFileNums(addUserVO.getUserFileNums())
                .userOrganization(addUserVO.getUserOrganization())
                .userAddress(addUserVO.getUserAddress())
                .build();

        if (userService.addUser(userDTO)) {
            log.info("用户名："+userName+ "---成功创建新用户信息");
            return ResponseEntity.ok("用户名："+userName+ "---成功创建新用户信息");
        } else {
            log.info("用户名："+userDTO.getUserName()+"---创建用户信息失败");
            return ResponseEntity.accepted().body("用户名："+userName+ "---创建用户信息失败");
        }
    }

    @GetMapping("/users/{userName}")
    public ResponseEntity<UserVO> getUserInfo(@PathVariable("userName") String userName) {
        // 根据UserName搜索出id
        if (!userService.isExist(userName)) {
            log.info("用户名：{}不存在", userName);
            return ResponseEntity.notFound().build();
        }
        UserVO userVO = userService.getUser(userName);
        log.info("用户名：{}获取用户信息成功\n用户信息为{}",userName,JSONObject.toJSONString(userVO, true));
        return ResponseEntity.ok(userVO);
    }

    @PutMapping("/users/{userName}")
    public ResponseEntity<UserVO> updateUserInfo(@PathVariable("userName") String userName,
                                             @Validated @RequestBody UserVO userVO) {
        // 根据UserName搜索出id
        if (!userService.isExist(userName)) {
            log.info("用户名：{}不存在", userName);
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = UserDTO.builder()
                .userName(userName)
                .userAddress(userVO.getUserAddress())
                .userOrganization(userVO.getUserOrganization())
                .userFileNums(userVO.getUserFileNums())
                .build();
        UserDTO updatedUser = userService.updateUserInfo(userDTO);
        UserVO result = UserVO.builder()
                .userName(updatedUser.getUserName())
                .userFileNums(updatedUser.getUserFileNums())
                .userOrganization(updatedUser.getUserOrganization())
                .userAddress(updatedUser.getUserAddress())
                .build();
        log.info("用户名：{}修改用户信息成功,用户信息修改为{}",userName, JSONObject.toJSONString(result, true));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/users/{userName}")
    public ResponseEntity<String> operateAUserInfoField(
            @PathVariable("userName") String userName,
            @Validated @RequestBody PatchUserInfoVO patchUserInfoVO) {
        // 根据UserName搜索出id
        if (!userService.isExist(userName)) {
            log.info("用户名：{}不存在", userName);
            return ResponseEntity.notFound().build();
        }

        String result;
        if ("/user-organization".equals(patchUserInfoVO.getPath())
                && "update".equals(patchUserInfoVO.getOp())) {
            UpdateOrganizationDTO user = UpdateOrganizationDTO.builder()
                    .userName(userName)
                    .userOrganization(patchUserInfoVO.getValue())
                    .build();
            result = userService.updateUserOrganization(user);
        } else if ("/user-address".equals(patchUserInfoVO.getPath())
                && "update".equals(patchUserInfoVO.getOp())) {
            UpdateAddressDTO user = UpdateAddressDTO.builder()
                    .userName(userName)
                    .userAddress(patchUserInfoVO.getValue())
                    .build();
            result = userService.updateUserAddress(user);
        } else if ("/user-file-nums".equals(patchUserInfoVO.getPath())
                && "update".equals(patchUserInfoVO.getOp())) {
            UpdateFileNumsDTO user = UpdateFileNumsDTO.builder()
                    .userName(userName)
                    .userFileNums(patchUserInfoVO.getValue())
                    .build();
            result = userService.updateUserFileNums(user);
        } else if ("/user-organization".equals(patchUserInfoVO.getPath())
                && "get".equals(patchUserInfoVO.getOp())) {
            result = userService.getUserOrganization(userName);
        } else if ("/user-address".equals(patchUserInfoVO.getPath())
                && "get".equals(patchUserInfoVO.getOp())) {
            result = userService.getUserAddress(userName);
        } else if ("/user-file-nums".equals(patchUserInfoVO.getPath())
                && "get".equals(patchUserInfoVO.getOp())) {
            result = userService.getUserFileNums(userName);
        } else {
            log.error("用户名：{}对用户信息进行了{}操作，\n操作失败", userName, patchUserInfoVO.getOp());
            return ResponseEntity.badRequest().build();
        }
        log.info("用户名：{}对用户信息进行了{}操作，\n操作成功，操作结果：{}", userName, patchUserInfoVO.getOp(), result);
        return ResponseEntity.ok(result);
    }

}
