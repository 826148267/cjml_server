package edu.jnu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

/**
 * @author Guo zifan
 * @date 2022年02月10日 20:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OsuSecondPhaseVO {
    @NotNull
    @JSONField(name = "osuFirstPhaseVO")
    private OsuFirstPhaseVO osuFirstPhaseVO;
    @NotNull
    @JSONField(name = "delta")
    private CipherText delta;
}
