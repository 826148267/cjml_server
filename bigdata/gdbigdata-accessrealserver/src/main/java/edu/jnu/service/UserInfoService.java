package edu.jnu.service;

import edu.jnu.config.OsuProtocolParams;
import edu.jnu.dao.UserInfoDao;
import edu.jnu.domain.CipherText;
import edu.jnu.domain.entity.UserInfo;
import edu.jnu.enums.ResponseEnum;
import edu.jnu.exception.ConditionException;
import edu.jnu.utils.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Guo zifan
 * @date 2022年01月28日 11:24
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 从real server的数据库的table_user_info表中的targetColumn列执行OSU的第一阶段
     * @param accessSet 访问时使用的全体混淆下标集合（含真实数据的下标）
     * @param taos tao向量
     * @param targetColumn  数据库表中的目标字段的字段名
     * @return 返回值为R，是目标明文加密到s+2层的密文
     */
    public CipherText selectionPhase(int[] accessSet,
                                     CipherText[] taos,
                                     String targetColumn) {
        // 产生0加密到s+2层的密文
        CipherText R = Action.Enc(OsuProtocolParams.pk, BigInteger.ZERO, OsuProtocolParams.s, OsuProtocolParams.s+2);
        // 利用访问下标序列accessSet从数据库中索引出对应的数据块Ci集合selectResult
        CipherText[] selectResult = new CipherText[accessSet.length];
        for (int i = 0; i < accessSet.length; i++) {
            Optional<UserInfo> optional = userInfoDao.findById(accessSet[i]);
            if (optional.isPresent()) {
                UserInfo userInfo = optional.get();
                Field field;
                try {
                    field = userInfo.getClass().getDeclaredField(targetColumn);
                    field.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_CANNOT_GET_FIELD);
                }
                String targetColumnValue;
                try {
                    targetColumnValue = (String) field.get(userInfo);
                } catch (IllegalAccessException e) {
                    throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_CANNOT_TRANSLATE);
                }
                selectResult[i] = new CipherText();
                selectResult[i].setCp(new BigInteger(targetColumnValue));
            } else {
                throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_NOT_FIND_TARGET);
            }
        }
        // 利用向量数组taos对数据库中的密文Ci进行同态乘法，获得一组结果Ti
        // 对结果进行同态加法运算R
        for (int i = 0; i < accessSet.length; i++) {
            CipherText tmp;
            try {
                tmp = Action.powCip(OsuProtocolParams.pk, taos[i], selectResult[i]);
            } catch (Exception e) {
                throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_HOMOMUL_FAIL);
            }
            CipherText T = new CipherText();
            T.setCp(tmp.getCp().mod(OsuProtocolParams.pk.getN().pow(OsuProtocolParams.s+3)));
            try {
                R = Action.HomoAdd(OsuProtocolParams.pk, R, T);
            } catch (Exception e) {
                throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_HOMOADD_FAIL);
            }
        }
        ArrayList S = new ArrayList();
        S.add(0,OsuProtocolParams.s);
        S.add(1,OsuProtocolParams.s+1);
        S.add(2,OsuProtocolParams.s+2);
        R.setS(S);
        R.setL(OsuProtocolParams.s);
        // 返回运算结果R
        return R;
    }

    /**
     * 从real server的数据库的table_user_info表中的targetColumn列执行OSU的第二阶段
     * @param accessSet 访问时使用的全体混淆下标集合（含真实数据的下标）
     * @param taos tao向量
     * @param targetColumn 数据库表中的目标字段的字段名
     * @param delta 利用delta、taos对所有数据块进行一系列计算能够将我们的指定的数据块更新成想要的值
     */
    public void updatePhase(int[] accessSet,
                            CipherText[] taos,
                            String targetColumn,
                            CipherText delta) {
        // 利用访问下标序列accessSet从数据库中索引出对应的数据块Ci集合
        CipherText[] selectResult = new CipherText[accessSet.length];
        for (int i = 0; i < accessSet.length; i++) {
            Optional<UserInfo> optional = userInfoDao.findById(accessSet[i]);
            if (optional.isPresent()) {
                UserInfo userInfo = optional.get();
                Field field;
                try {
                    field = userInfo.getClass().getDeclaredField(targetColumn);
                    field.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_CANNOT_GET_FIELD);
                }
                String targetColumnValue;
                try {
                    targetColumnValue = (String) field.get(userInfo);
                } catch (IllegalAccessException e) {
                    throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_CANNOT_TRANSLATE);
                }
                selectResult[i] = new CipherText();
                selectResult[i].setCp(new BigInteger(targetColumnValue));
                ArrayList S = new ArrayList();
                S.add(0, OsuProtocolParams.s+2);
                selectResult[i].setS(S);
                selectResult[i].setL(OsuProtocolParams.s);

                // 利用delta对taos依次做同态乘，结果保存在taos中
                // 利用数据块Ci和tao依次做同态加,结果保存在Ci集合中
                taos[i].setCp(taos[i].getCp().mod(OsuProtocolParams.pk.getN().pow(OsuProtocolParams.s+2)));
                try {
                    selectResult[i] = Action.HomoAdd(OsuProtocolParams.pk, selectResult[i], Action.powCip(OsuProtocolParams.pk, taos[i], delta));
                } catch (Exception e) {
                    throw new ConditionException(ResponseEnum.UPDATE_PHASE_FAIL_COMPUTE_FRESH_CIPHERTEXT_FAIL);
                }
                // 利用Ci集合重新更新数据库中的密文块
                try {
                    field.set(userInfo, String.valueOf(selectResult[i].getCp()));
                } catch (IllegalAccessException e) {
                    throw new ConditionException(ResponseEnum.UPDATE_PHASE_FAIL_SET_FIELD_FAIL);
                }
                userInfoDao.save(userInfo);
            } else {
                throw new ConditionException(ResponseEnum.SELECTION_PHASE_FAIL_NOT_FIND_TARGET);
            }
        }
    }

    public int createUser(UserInfo userInfo) {
        UserInfo result = userInfoDao.save(userInfo);
        return result.getId();
    }
}
