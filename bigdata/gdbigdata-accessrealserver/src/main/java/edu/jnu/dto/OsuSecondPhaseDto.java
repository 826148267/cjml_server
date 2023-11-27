package edu.jnu.dto;

import edu.jnu.domain.CipherText;

/**
 * @author Guo zifan
 * @date 2022年02月10日 20:41
 */
public class OsuSecondPhaseDto {
    private OsuFirstPhaseDto osuFirstPhaseDto;
    private CipherText delta;

    public OsuFirstPhaseDto getOsuFirstPhaseDto() {
        return osuFirstPhaseDto;
    }

    public void setOsuFirstPhaseDto(OsuFirstPhaseDto osuFirstPhaseDto) {
        this.osuFirstPhaseDto = osuFirstPhaseDto;
    }

    public CipherText getDelta() {
        return delta;
    }

    public void setDelta(CipherText delta) {
        this.delta = delta;
    }
}
