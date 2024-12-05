package edu.jnu.VO;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月04日 21:02
 */
public class IntegerityProof {
    @JSONField(name = "alpha")
    private String alpha;
    @JSONField(name = "gamma")
    private String gamma;
    @JSONField(name = "R")
    private String R;

    public IntegerityProof() {
    }

    public IntegerityProof(String alpha, String gamma, String R) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.R = R;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getGamma() {
        return gamma;
    }

    public void setGamma(String gamma) {
        this.gamma = gamma;
    }

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }
}
