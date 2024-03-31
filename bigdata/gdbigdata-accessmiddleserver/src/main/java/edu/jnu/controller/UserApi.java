package edu.jnu.controller;

import com.alibaba.fastjson.JSONObject;
import edu.jnu.domain.User;
import edu.jnu.dto.PatchUserInfoDto;
import edu.jnu.dto.UserDto;
import edu.jnu.response.controller.CtrlResEnum;
import edu.jnu.service.UserService;
import edu.jnu.utils.JsonResponse;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Guo zifan
 * @date 2022年01月30日 20:10
 */
@RestController
@Api(tags = "用户信息操作接口")
public class UserApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private UserService userService;

    @PostMapping ("/users")
    @ApiOperation("创建用户信息接口")
    public JsonResponse<?> createUser(@Validated @RequestBody UserDto userDto) {
        // 判断用户是否已经存在
        if (userService.isExist(userDto.getUserName())) {
            LOGGER.info("用户名："+userDto.getUserName()+"---试图重复创建用户资料，已拒绝创建");
            return new JsonResponse<>(CtrlResEnum.CREATE_USER_FAIL, "用户已存在，禁止重复创建");
        }

        User user = new User(userDto.getUserName(),
                userDto.getUserAddress(),
                userDto.getUserOrganization(),
                userDto.getUserFileNums());

        // 如果没有问题就调用real server的接口，将数据存到数据库
        boolean flag = userService.createNewUser(user);

        if (flag) {
            LOGGER.info("用户名："+userDto.getUserName()+"---成功创建新用户信息");
            return new JsonResponse<>(CtrlResEnum.SUCCESS, "您已成功创建用户信息");
        } else {
            LOGGER.info("用户名："+userDto.getUserName()+"---创建用户信息失败");
            return new JsonResponse<>(CtrlResEnum.CREATE_USER_FAIL, "创建用户信息失败");
        }
    }

    @GetMapping("/users/{userName}")
    @ApiOperation("获取用户信息接口")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "path", example = "id666")
    public JsonResponse<User> getUserInfo(@PathVariable("userName") String userName) {
        // 根据UserName搜索出id
        if (!userService.isExist(userName)) {
            LOGGER.info("用户名："+userName+"不存在");
            return new JsonResponse<>(CtrlResEnum.USER_NO_EXIST);
        }
        User user = userService.getUser(userName);
        LOGGER.info("用户名："+userName+"获取用户信息成功\n"
                +"用户信息为"+ JSONObject.toJSONString(user, true));
        return new JsonResponse<>(CtrlResEnum.SUCCESS, user);
    }

    @PutMapping("/users/{userName}")
    @ApiOperation("更新用户信息接口（建议一条用户信息记录中多个字段值发生改变时使用）")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "path", example = "id666")
    public JsonResponse<User> updateUserInfo(@PathVariable("userName") String userName,
                                             @Validated @RequestBody UserDto userDto) {
        // 根据UserName搜索出id
        if (!userService.isExist(userName)) {
            LOGGER.info("用户名："+userName+"不存在");
            return new JsonResponse<>(CtrlResEnum.USER_NO_EXIST);
        }
        User user = new User(
                userDto.getUserName(),
                userDto.getUserAddress(),
                userDto.getUserOrganization(),
                userDto.getUserFileNums());
        User updatedUser = userService.updateUserInfo(user);
        LOGGER.info("用户名："+userName+"修改用户信息成功\n"
                +"用户信息由"+JSONObject.toJSONString(user, true)+"\n"
                +"修改为"+JSONObject.toJSONString(updatedUser, true));
        return new JsonResponse<>(CtrlResEnum.SUCCESS, updatedUser);
    }

    @PatchMapping("/users/{userName}")
    @ApiOperation("查询或者修改一条用户信息的某个字段")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "path", example = "id666")
    public JsonResponse<String> operateAUserInfoField(
            @PathVariable("userName") String userName,
            @Validated @RequestBody PatchUserInfoDto<String> patchUserInfoDto) {
        // 根据UserName搜索出id
        if (!userService.isExist(userName)) {
            LOGGER.info("用户名："+userName+"不存在");
            return new JsonResponse<>(CtrlResEnum.USER_NO_EXIST);
        }

        String result;
        if ("/user-organization".equals(patchUserInfoDto.getPath())
                && "update".equals(patchUserInfoDto.getOp())) {
            User user = new User();
            user.setUserName(userName);
            user.setUserOrganization(patchUserInfoDto.getValue());
            result = userService.updateUserOrganization(user);
        } else if ("/user-address".equals(patchUserInfoDto.getPath())
                && "update".equals(patchUserInfoDto.getOp())) {
            User user = new User();
            user.setUserName(userName);
            user.setUserAddress(patchUserInfoDto.getValue());
            result = userService.updateUserAddress(user);
        } else if ("/user-file-nums".equals(patchUserInfoDto.getPath())
                && "update".equals(patchUserInfoDto.getOp())) {
            User user = new User();
            user.setUserName(userName);
            user.setUserFileNums(patchUserInfoDto.getValue());
            result = userService.updateUserFileNums(user);
        } else if ("/user-organization".equals(patchUserInfoDto.getPath())
                && "get".equals(patchUserInfoDto.getOp())) {
            result = userService.getUserOrganization(userName);
        } else if ("/user-address".equals(patchUserInfoDto.getPath())
                && "get".equals(patchUserInfoDto.getOp())) {
            result = userService.getUserAddress(userName);
        } else if ("/user-file-nums".equals(patchUserInfoDto.getPath())
                && "get".equals(patchUserInfoDto.getOp())) {
            result = userService.getUserFileNums(userName);
        } else {
            LOGGER.debug("用户名："+userName+"对用户信息进行了"+patchUserInfoDto.getOp()+"操作，\n"+"操作失败");
            return new JsonResponse<>(CtrlResEnum.EXCEPTION, "接口未实现");
        }
        LOGGER.debug("用户名："+userName+"对用户信息进行了"+patchUserInfoDto.getOp()+"操作，\n"+"操作成功，操作结果："+result);
        return new JsonResponse<>(CtrlResEnum.SUCCESS, result);
    }
}
