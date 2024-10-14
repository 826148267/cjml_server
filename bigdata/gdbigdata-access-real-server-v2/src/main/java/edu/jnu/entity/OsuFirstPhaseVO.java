package edu.jnu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * @author Guo zifan
 * @date 2022年02月10日 20:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OsuFirstPhaseVO {
    @NotEmpty(message = "下标集合accessSet不能为空")
    @JSONField(name = "accessSet")
    private ArrayList<Integer> accessSet;
    @NotEmpty(message = "向量taos不能为空")
    @JSONField(name = "taoVector")
    private ArrayList<CipherText> taoVector;
    @NotNull(message = "目标字段不能为空")
    @JSONField(name = "targetColumn")
    private String targetColumn;
}
