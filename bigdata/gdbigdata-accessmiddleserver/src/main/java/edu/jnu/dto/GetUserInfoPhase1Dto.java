package edu.jnu.dto;

import com.alibaba.fastjson.annotation.JSONField;
import edu.jnu.domain.CipherText;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月14日 22:08
 */
public class GetUserInfoPhase1Dto {
    @JSONField(name = "taos")
    private CipherText[] taoVector;
    @JSONField(name = "targetColumn")
    private String targetColumn;
    @JSONField(name = "accessSet")
    private int[] idSet;

    public GetUserInfoPhase1Dto() {
    }

    public GetUserInfoPhase1Dto(CipherText[] taoVector, String targetColumn, int[] idSet) {
        this.taoVector = taoVector;
        this.targetColumn = targetColumn;
        this.idSet = idSet;
    }

    public CipherText[] getTaoVector() {
        return taoVector;
    }

    public void setTaoVector(CipherText[] taoVector) {
        this.taoVector = taoVector;
    }

    public String getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
    }

    public int[] getIdSet() {
        return idSet;
    }

    public void setIdSet(int[] idSet) {
        this.idSet = idSet;
    }
}
