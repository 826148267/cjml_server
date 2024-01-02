package edu.jnu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月14日 22:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserInfoPhase1DTO {
    @JsonProperty("taoVector")
    private ArrayList<CipherText> taoVector;
    @JsonProperty("targetColumn")
    private String targetColumn;
    @JsonProperty("accessSet")
    private ArrayList<Integer> accessSet;
}
