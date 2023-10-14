package edu.jnu.service;

import edu.jnu.config.OSU.OsuProtocolParams;
import edu.jnu.entity.CipherText;
import edu.jnu.entity.GetUserInfoPhase1DTO;
import edu.jnu.entity.GetUserInfoPhase2DTO;
import edu.jnu.entity.OsuParam;
import edu.jnu.service.thirdpart.UserInfoService;
import edu.jnu.utils.Action;
import edu.jnu.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月16日 18:59
 */
@Service
@Slf4j
public class OsuService {

    @Autowired
    private UserInfoService userInfoService;

    public String getOneFieldOfTable(OsuParam osuParam) {
        HashMap<String, Object> result = this.selectionPhase(osuParam);

        GetUserInfoPhase1DTO dto1 = (GetUserInfoPhase1DTO) result.get("dto1");
        CipherText V = (CipherText) result.get("V");
        String message = result.get("message").toString();

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            this.updatePhase(osuParam, dto1, V, message);
            return null;
        });
        future.whenComplete((r, e) -> log.info("值：{}更新完成", message));
        return message;
    }

    public String setOneFieldOfTable(OsuParam osuParam, String freshValue) {
        HashMap<String, Object> result = this.selectionPhase(osuParam);

        GetUserInfoPhase1DTO dto1 = (GetUserInfoPhase1DTO) result.get("dto1");
        CipherText V = (CipherText) result.get("V");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            this.updatePhase(osuParam, dto1, V, freshValue);
            return null;
        });
        future.whenComplete((r, e) -> log.info("值：{}更新完成", freshValue));
        return freshValue;
    }

    private void updatePhase(OsuParam osuParam, GetUserInfoPhase1DTO dto1, CipherText v, String freshValue) {
        // 创建update阶段的dto
        GetUserInfoPhase2DTO dto2 = osuParam.createDto2(dto1, v, Tools.encode(freshValue));

        // 更新数据库的数据
        userInfoService.osuUpdate(dto2);
    }

    private HashMap<String, Object> selectionPhase(OsuParam osuParam) {
        HashMap<String, Object> result = new HashMap<>();

        // 系统参数简单校验
        if (!osuParam.validParams()) {
            log.error("系统参数不符合要求");
            return null;
        }

        // 创建selection阶段数据传输对象
        GetUserInfoPhase1DTO dto1 = osuParam.createDto1ForSingleField();

        // 调用real server接口得到R
        CipherText R = userInfoService.osuGetR(dto1);

        // 从R中获得V
        CipherText V;
        try {
            V = Action.getVFromR(OsuProtocolParams.SK, R);
        } catch (Exception e) {
            log.error("获取加密后的密文失败");
            return null;
        }
        // 从V中继续解密获得明文
        String oneFieldOfTableValue;
        try {
            oneFieldOfTableValue = Action.getPlainFromV(OsuProtocolParams.SK, V);
        } catch (Exception e) {
            log.error("解密失败");
            return null;
        }

        result.put("dto1", dto1);
        result.put("V", V);
        result.put("message", oneFieldOfTableValue);
        return result;
    }
}
