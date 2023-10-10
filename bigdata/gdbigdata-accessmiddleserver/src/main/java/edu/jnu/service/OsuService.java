package edu.jnu.service;

import edu.jnu.config.OsuProtocolParams;
import edu.jnu.domain.CipherText;
import edu.jnu.domain.OsuParam;
import edu.jnu.dto.GetUserInfoPhase1Dto;
import edu.jnu.dto.GetUserInfoPhase2Dto;
import edu.jnu.exception.ConditionException;
import edu.jnu.response.service.SrvResEnum;
import edu.jnu.utils.Action;
import edu.jnu.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月16日 18:59
 */
@Service
public class OsuService {

    @Autowired
    private ApiManagerService apiManagerService;

    public String getOneFieldOfTable(OsuParam osuParam) {
        HashMap<String, Object> result = this.selectionPhase(osuParam);

        GetUserInfoPhase1Dto dto1 = (GetUserInfoPhase1Dto) result.get("dto1");
        CipherText V = (CipherText) result.get("V");
        String message = result.get("message").toString();

        this.updatePhase(osuParam, dto1, V, message);

        return message;
    }

    public String setOneFieldOfTable(OsuParam osuParam, String freshValue) {
        HashMap<String, Object> result = this.selectionPhase(osuParam);

        GetUserInfoPhase1Dto dto1 = (GetUserInfoPhase1Dto) result.get("dto1");
        CipherText V = (CipherText) result.get("V");

        this.updatePhase(osuParam, dto1, V, freshValue);

        return freshValue;
    }

    private void updatePhase(OsuParam osuParam, GetUserInfoPhase1Dto dto1, CipherText v, String freshValue) {
        // 创建update阶段的dto
        GetUserInfoPhase2Dto dto2 = osuParam.createDto2(dto1, v, Tools.encode(freshValue));

        // 更新数据库的数据
        apiManagerService.osuUpdate(dto2, osuParam.getOsuUpdateUrl());
    }

    private HashMap<String, Object> selectionPhase(OsuParam osuParam) {
        HashMap<String, Object> result = new HashMap<>();

        // 系统参数简单校验
        if (!osuParam.validParams()) {
            throw new ConditionException(SrvResEnum.OSU_PARAM_VALID_FAIL);
        }

        // 创建selection阶段数据传输对象
        GetUserInfoPhase1Dto dto1;
        try {
            dto1 = osuParam.createDto1ForSingleField();
        } catch (Exception e) {
            throw new ConditionException(SrvResEnum.CREATE_DTO1_FAIL);
        }

        // 调用real server接口得到R
        CipherText R = apiManagerService.osuGetR(dto1, osuParam.getGetRUrl());

        // 从R中获得V
        CipherText V;
        try {
            V = Action.getVFromR(OsuProtocolParams.SK, R);
        } catch (Exception e) {
            throw new ConditionException(SrvResEnum.OSU_GET_V_DEC_FAIL);
        }
        // 从V中继续解密获得明文
        String oneFieldOfTableValue;
        try {
            oneFieldOfTableValue = Action.getPlainFromV(OsuProtocolParams.SK, V);
        } catch (Exception e) {
            throw new ConditionException(SrvResEnum.OSU_GET_PLAIN_DEC_FAIL);
        }

        result.put("dto1", dto1);
        result.put("V", V);
        result.put("message", oneFieldOfTableValue);
        return result;
    }
}
