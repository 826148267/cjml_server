package edu.jnu.entity;

import edu.jnu.config.OSU.OsuProtocolParams;
import edu.jnu.utils.Action;
import edu.jnu.utils.Tools;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月16日 20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OsuParam {
    private int id;
    private String targetColumn;
    private String tableRecordNum;
    public GetUserInfoPhase1DTO createDto1ForSingleField() {
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

        CipherText[] taos = Action.createTaoVector(OsuProtocolParams.PK,
                OsuProtocolParams.s, randomSequenceSize, i);

        // 将数组idSet转换为ArrayList<Integer>
        ArrayList<Integer> accessSet = new ArrayList<>();
        for (int k : idSet) {
            accessSet.add(k);
        }
        // 将数组taos转换为ArrayList<CipherText>
        ArrayList<CipherText> taoVector = new ArrayList<>(Arrays.asList(taos));

        return new GetUserInfoPhase1DTO(taoVector, targetColumn, accessSet);
    }

    public GetUserInfoPhase2DTO createDto2(GetUserInfoPhase1DTO dto1, CipherText v, String freshValue) {
        CipherText delta = Action.createDelta(OsuProtocolParams.PK, OsuProtocolParams.s, v, freshValue);
        return new GetUserInfoPhase2DTO(dto1, delta);
    }

    public boolean validParams() {
        if (this.id == 0) {
            return false;
        }
        if (this.tableRecordNum == null) {
            return false;
        }
        return this.targetColumn != null;
    }
}
