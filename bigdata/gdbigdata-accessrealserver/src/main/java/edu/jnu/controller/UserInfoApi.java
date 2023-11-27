package edu.jnu.controller;

import edu.jnu.domain.CipherText;
import edu.jnu.domain.entity.UserInfo;
import edu.jnu.dto.CreateUserDto;
import edu.jnu.dto.OsuFirstPhaseDto;
import edu.jnu.dto.OsuSecondPhaseDto;
import edu.jnu.enums.ResponseEnum;
import edu.jnu.service.UserInfoService;
import edu.jnu.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Guo zifan
 * @date 2022年01月24日 19:02
 */
@RestController
@RequestMapping("/rs")
public class UserInfoApi {

    @Autowired
    private UserInfoService userInfoService;

    /*
    osu协议第一阶段操作
     */
    @RequestMapping(value = "/osu-operation-first-phase", method = RequestMethod.POST)
    public JsonResponse<?> getR(@RequestBody OsuFirstPhaseDto osuFirstPhaseDto) {
        int[] accessSet = osuFirstPhaseDto.getAccessSet();
        CipherText[] taos = osuFirstPhaseDto.getTaos();
        String targetColumn = osuFirstPhaseDto.getTargetColumn();
        CipherText R = userInfoService.selectionPhase(accessSet, taos, targetColumn);
        if (R != null) {
            return new JsonResponse<>(ResponseEnum.SUCCESS, R);
        }
        return new JsonResponse<>(ResponseEnum.SELECTION_PHASE_FAIL);
    }

    /*
    osu协议第二阶段操作
     */
    @RequestMapping(value = "/osu-operation-second-phase", method = RequestMethod.POST)
    public JsonResponse<?> updateTargetCipherTextSet(@RequestBody OsuSecondPhaseDto osuSecondPhaseDto) {
        int[] accessSet = osuSecondPhaseDto.getOsuFirstPhaseDto().getAccessSet();
        CipherText[] taos = osuSecondPhaseDto.getOsuFirstPhaseDto().getTaos();
        String targetColumn = osuSecondPhaseDto.getOsuFirstPhaseDto().getTargetColumn();
        CipherText delta = osuSecondPhaseDto.getDelta();
        userInfoService.updatePhase(accessSet, taos, targetColumn, delta);
        return new JsonResponse<>(ResponseEnum.SUCCESS);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public JsonResponse<?> createNewUserInformation(@Validated @RequestBody CreateUserDto createUserDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserOrganization(createUserDto.getUserOrganization());
        userInfo.setUserName(createUserDto.getUserName());
        userInfo.setUserAddress(createUserDto.getUserAddress());
        userInfo.setUserFileNums(createUserDto.getUserFileNums());
        int resultId = userInfoService.createUser(userInfo);
        return new JsonResponse<>(ResponseEnum.SUCCESS, resultId);
    }
}
