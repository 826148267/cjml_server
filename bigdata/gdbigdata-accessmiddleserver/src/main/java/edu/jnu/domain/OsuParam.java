package edu.jnu.domain;

import edu.jnu.config.OsuProtocolParams;
import edu.jnu.dto.GetUserInfoPhase1Dto;
import edu.jnu.dto.GetUserInfoPhase2Dto;
import edu.jnu.utils.Action;
import edu.jnu.utils.Tools;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月16日 20:16
 */
public class OsuParam {
    private int id;
    private String targetColumn;
    private String tableRecordNum;
    private String getRUrl;
    private String osuUpdateUrl;

    public OsuParam() {
    }

    public OsuParam(int id, String targetColumn, String tableRecordNum, String getRUrl, String osuUpdateUrl) {
        this.id = id;
        this.targetColumn = targetColumn;
        this.tableRecordNum = tableRecordNum;
        this.getRUrl = getRUrl;
        this.osuUpdateUrl = osuUpdateUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
    }

    public String getTableRecordNum() {
        return tableRecordNum;
    }

    public void setTableRecordNum(String tableRecordNum) {
        this.tableRecordNum = tableRecordNum;
    }

    public String getGetRUrl() {
        return getRUrl;
    }

    public void setGetRUrl(String getRUrl) {
        this.getRUrl = getRUrl;
    }

    public String getOsuUpdateUrl() {
        return osuUpdateUrl;
    }

    public void setOsuUpdateUrl(String osuUpdateUrl) {
        this.osuUpdateUrl = osuUpdateUrl;
    }

    public GetUserInfoPhase1Dto createDto1ForSingleField() throws Exception {
        // 获取当前表中的记录数
        int recordNum = Integer.parseInt(tableRecordNum);

        // 获取随机访问下标集合的大小
        int randomSequenceSize = OsuProtocolParams.randomSequenceSize;
        while (recordNum < randomSequenceSize) {
            randomSequenceSize /= 2;
        }
        if (randomSequenceSize == 0) {
            return null;
        }

        int[] idSet = Tools.createRandomIndexSet(randomSequenceSize, recordNum+1, 1);
        int i = Tools.getRandom(randomSequenceSize, 0);
        idSet[i] = id;

        CipherText[] taoVector = Action.createTaoVector(OsuProtocolParams.PK,
                OsuProtocolParams.s, randomSequenceSize, i);

        return new GetUserInfoPhase1Dto(
                taoVector, targetColumn, idSet
        );
    }

    public GetUserInfoPhase2Dto createDto2(GetUserInfoPhase1Dto dto1, CipherText v, String freshValue) {
        CipherText delta = Action.createDelta(OsuProtocolParams.PK,
                OsuProtocolParams.s, v, freshValue);

        return new GetUserInfoPhase2Dto(
                dto1, delta
        );
    }

    public boolean validParams() {
        if (this.id == 0) return false;
        if (this.osuUpdateUrl == null) return false;
        if (this.getRUrl == null) return false;
        if (this.tableRecordNum == null) return false;
        if (this.targetColumn == null) return false;
        return true;
    }
}
