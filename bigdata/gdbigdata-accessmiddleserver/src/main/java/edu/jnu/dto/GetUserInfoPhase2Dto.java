package edu.jnu.dto;

import com.alibaba.fastjson.annotation.JSONField;
import edu.jnu.domain.CipherText;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月15日 10:58
 */
public class GetUserInfoPhase2Dto {
    @JSONField(name = "osuFirstPhaseDto")
    private GetUserInfoPhase1Dto getUserInfoPhase1Dto;
    @JSONField(name = "delta")
    private CipherText delta;



    public GetUserInfoPhase2Dto() {
    }

    public GetUserInfoPhase2Dto(GetUserInfoPhase1Dto dto1, CipherText delta) {
        this.getUserInfoPhase1Dto = dto1;
        this.delta = delta;
    }

    public CipherText getDelta() {
        return delta;
    }

    public void setDelta(CipherText delta) {
        this.delta = delta;
    }

    public GetUserInfoPhase1Dto getGetUserInfoPhase1Dto() {
        return getUserInfoPhase1Dto;
    }

    public void setGetUserinfoPhase1Dto(GetUserInfoPhase1Dto getUserInfoPhase1Dto) {
        this.getUserInfoPhase1Dto = getUserInfoPhase1Dto;
    }
}
