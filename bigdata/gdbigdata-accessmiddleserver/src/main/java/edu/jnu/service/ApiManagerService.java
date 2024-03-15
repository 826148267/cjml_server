package edu.jnu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.jnu.domain.CipherText;
import edu.jnu.dto.GetUserInfoPhase1Dto;
import edu.jnu.dto.GetUserInfoPhase2Dto;
import edu.jnu.dto.UserDto;
import edu.jnu.exception.ConditionException;
import edu.jnu.response.service.SrvResEnum;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月15日 13:27
 */
@Service
public class ApiManagerService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiManagerService.class);

    @Value("${real.server.url.add.userinfo}")
    private String addUserUrl;

    public CipherText osuGetR(GetUserInfoPhase1Dto dto, String url) {
        JSONObject result = this.simplePost(dto, url);

        if (!"200".equals(result.get("code").toString())) {
            throw new ConditionException(SrvResEnum.EXCEPTION);
        }

        JSONObject rObj = result.getJSONObject("data");
        JSONArray sJArr = rObj.getJSONArray("s");
        String sJAStr = JSONObject.toJSONString(sJArr);
        ArrayList<Integer> S = (ArrayList<Integer>) JSONObject.parseArray(sJAStr, Integer.class);
        BigInteger cp = rObj.getObject("cp", BigInteger.class);
        int l = (int) rObj.get("l");
        return new CipherText(cp, l, S);
    }

    public void osuUpdate(GetUserInfoPhase2Dto dto, String url) {
        JSONObject result = this.simplePost(dto, url);

        if (!"200".equals(result.get("code").toString())) {
            throw new ConditionException(SrvResEnum.EXCEPTION);
        }
    }

    public String createUser(UserDto userDto) {
        JSONObject result = this.simplePost(userDto, addUserUrl);
        if ("200".equals(result.get("code"))) {
            return result.get("data").toString();
        } else {
            return null;
        }
    }

    private JSONObject simplePost(Object obj, String url) {
        String userDtoStr = JSONObject.toJSONString(obj, true);
        String resStr = null;
        try {
            resStr = EntityUtils.toString(
                    Request.Post(url)
                            .bodyString(userDtoStr, ContentType.APPLICATION_JSON)
                            .execute().returnResponse().getEntity(),
                    "UTF-8"
            );
        } catch (IOException e) {
            // TODO 此处异常处理有问题，如果发生网络抖动整个程序都会宕机，待修改
            LOGGER.info("middleserver调用realserver接口时，产生问题");
        }
        return JSON.parseObject(resStr);
    }
}
