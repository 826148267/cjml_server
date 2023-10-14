package edu.jnu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月15日 10:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserInfoPhase2DTO {
    @JsonProperty("osuFirstPhaseVO")
    private GetUserInfoPhase1DTO getUserInfoPhase1DTO;
    @JsonProperty("delta")
    private CipherText delta;
}
