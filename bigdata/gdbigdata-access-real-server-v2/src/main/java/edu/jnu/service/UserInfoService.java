package edu.jnu.service;

import edu.jnu.config.OsuProtocolParams;
import edu.jnu.dao.UserInfoDao;
import edu.jnu.entity.CipherText;
import edu.jnu.entity.TaoAndCdata;
import edu.jnu.entity.UserInfo;
import edu.jnu.utils.Action;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

/**
 * @author Guo zifan
 * @date 2022年01月28日 11:24
 */
@Service
@Slf4j
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    public int createUser(UserInfo userInfo) {
        UserInfo result = userInfoDao.save(userInfo);
        return result.getId();
    }

//    /**
//     * (串行版)
//     * 从real server的数据库的table_user_info表中的targetColumn列执行OSU的第一阶段
//     * @param accessSet 访问时使用的全体混淆下标集合（含真实数据的下标）
//     * @param taos tao向量
//     * @param targetColumn  数据库表中的目标字段的字段名
//     * @return 返回值为R，是目标明文加密到s+2层的密文
//     */
//    public CipherText selectionPhase(final ArrayList<Integer> accessSet,
//                                     ArrayList<CipherText> taos,
//                                     String targetColumn) {
//        // 产生0加密到s+2层的密文
//        CipherText R = Action.Enc(OsuProtocolParams.pk, BigInteger.ZERO, OsuProtocolParams.s, OsuProtocolParams.s+2);
//        // 利用访问下标序列accessSet从数据库中索引出对应的数据块Ci集合selectResult
//        CipherText[] selectResult = new CipherText[accessSet.size()];
//        for (int i = 0; i < accessSet.size(); i++) {
//            Optional<UserInfo> optional = userInfoDao.findById(accessSet.get(i));
//            if (optional.isPresent()) {
//                UserInfo userInfo = optional.get();
//                Field field;
//                try {
//                    field = userInfo.getClass().getDeclaredField(targetColumn);
//                    field.setAccessible(true);
//                } catch (NoSuchFieldException e) {
//                    log.error("找不到该字段。报错为{}",e.getMessage());
//                    return null;
//                }
//                String targetColumnValue;
//                try {
//                    targetColumnValue = (String) field.get(userInfo);
//                } catch (IllegalAccessException e) {
//                    log.error("无权访问该字段的值。报错为{}", e.getMessage());
//                    return null;
//                }
//                selectResult[i] = new CipherText();
//                selectResult[i].setCp(new BigInteger(targetColumnValue));
//            } else {
//                log.info("用户：{}未被注册", accessSet.get(i));
//            }
//        }
//        // 利用向量数组taos对数据库中的密文Ci进行同态乘法，获得一组结果Ti
//        // 对结果进行同态加法运算R
//        for (int i = 0; i < accessSet.size(); i++) {
//            CipherText tmp = Action.powCip(OsuProtocolParams.pk, taos.get(i), selectResult[i]);
//            CipherText T = new CipherText();
//            T.setCp(tmp.getCp().mod(OsuProtocolParams.pk.getN().pow(OsuProtocolParams.s+3)));
//            R = Action.HomoAdd(OsuProtocolParams.pk, R, T);
//        }
//        ArrayList S = new ArrayList();
//        S.add(0,OsuProtocolParams.s);
//        S.add(1,OsuProtocolParams.s+1);
//        S.add(2,OsuProtocolParams.s+2);
//        R.setS(S);
//        R.setL(OsuProtocolParams.s);
//        // 返回运算结果R
//        return R;
//    }

    /**
     * (并行版)
     * 从real server的数据库的table_user_info表中的targetColumn列执行OSU的第一阶段
     * @param accessSet 访问时使用的全体混淆下标集合（含真实数据的下标）
     * @param taos tao向量
     * @param targetColumn  数据库表中的目标字段的字段名
     * @return 返回值为R，是目标明文加密到s+2层的密文
     */
    public CipherText selectionPhase(final ArrayList<Integer> accessSet,
                                     ArrayList<CipherText> taos,
                                     String targetColumn) {
        // 用于计算R的参数集合
        ArrayList<TaoAndCdata> taoAndCdatas = new ArrayList<>();

        // 利用访问下标序列accessSet从数据库中索引出对应的数据块Ci集合selectResult
        CipherText[] selectResult = new CipherText[accessSet.size()];
        for (int i = 0; i < accessSet.size(); i++) {
            Optional<UserInfo> optional = userInfoDao.findById(accessSet.get(i));
            if (optional.isPresent()) {
                UserInfo userInfo = optional.get();
                Field field;
                String targetColumnValue;
                try {
                    field = userInfo.getClass().getDeclaredField(targetColumn);
                    field.setAccessible(true);
                    targetColumnValue = (String) field.get(userInfo);
                } catch (IllegalAccessException e) {
                    log.error("无权访问该字段的值。报错为{}", e.getMessage());
                    return null;
                } catch (NoSuchFieldException e) {
                    log.error("找不到该字段。报错为{}", e.getMessage());
                    return null;
                }
                selectResult[i] = new CipherText();
                selectResult[i].setCp(new BigInteger(targetColumnValue));
            } else {
                log.info("用户：{}未被注册", accessSet.get(i));
                return null;
            }

            // 将参数装入集合
            TaoAndCdata taoAndCdata = new TaoAndCdata(accessSet.get(i), taos.get(i), selectResult[i]);
            taoAndCdatas.add(taoAndCdata);
        }

        // 利用向量数组taos对数据库中的密文Ci进行同态乘法，获得一组结果Ti
        // 对结果进行同态加法运算R
        // 产生0加密到s+2层的密文
        CipherText initSum = Action.Enc(OsuProtocolParams.pk, BigInteger.ZERO, OsuProtocolParams.s, OsuProtocolParams.s+2);
        CipherText R = taoAndCdatas.stream().parallel()
                .map(taoAndCdata -> {
                    CipherText tmp = Action.powCip(OsuProtocolParams.pk, taoAndCdata.getTao(), taoAndCdata.getCdata());
                    CipherText T = new CipherText();
                    T.setCp(tmp.getCp().mod(OsuProtocolParams.pk.getN().pow(OsuProtocolParams.s + 3)));
                    return T;
                })
                .reduce(initSum, (sum, elem) -> Action.HomoAdd(OsuProtocolParams.pk, sum, elem));
        ArrayList S = new ArrayList();
        S.add(0, OsuProtocolParams.s);
        S.add(1,OsuProtocolParams.s+1);
        S.add(2,OsuProtocolParams.s+2);
        R.setS(S);
        R.setL(OsuProtocolParams.s);
        // 返回运算结果R
        return R;
    }

    /**
     * 从real server的数据库的table_user_info表中的targetColumn列执行OSU的第二阶段
     * @param indexAndTaoMap key为访问时使用的全体混淆下标集合（含真实数据的下标），value为对应的tao向量
     * @param targetColumn 数据库表中的目标字段的字段名
     * @param delta 利用delta、taos对所有数据块进行一系列计算能够将我们的指定的数据块更新成想要的值
     */
    public void updatePhase(HashMap<Integer, CipherText> indexAndTaoMap,
                            String targetColumn,
                            CipherText delta) {
//        // 利用访问下标序列accessSet从数据库中索引出对应的数据块Ci集合
//        CipherText[] selectResult = new CipherText[accessSet.size()];
//        for (int i = 0; i < accessSet.size(); i++) {
//            Optional<UserInfo> optional = userInfoDao.findById(accessSet.get(i));
//            if (optional.isPresent()) {
//                UserInfo userInfo = optional.get();
//                Field field;
//                try {
//                    field = userInfo.getClass().getDeclaredField(targetColumn);
//                    field.setAccessible(true);
//                } catch (NoSuchFieldException e) {
//                    log.error("找不到该字段。报错为{}",e.getMessage());
//                    return;
//                }
//                String targetColumnValue;
//                try {
//                    targetColumnValue = (String) field.get(userInfo);
//                } catch (IllegalAccessException e) {
//                    log.error("无权访问该字段的值。报错为{}", e.getMessage());
//                    return;
//                }
//                selectResult[i] = new CipherText();
//                selectResult[i].setCp(new BigInteger(targetColumnValue));
//                ArrayList S = new ArrayList();
//                S.add(0, OsuProtocolParams.s+2);
//                selectResult[i].setS(S);
//                selectResult[i].setL(OsuProtocolParams.s);
//
//                // 利用delta对taos依次做同态乘，结果保存在taos中
//                // 利用数据块Ci和tao依次做同态加,结果保存在Ci集合中
//                taos.get(i).setCp(taos.get(i).getCp().mod(OsuProtocolParams.pk.getN().pow(OsuProtocolParams.s+2)));
//                selectResult[i] = Action.HomoAdd(OsuProtocolParams.pk, selectResult[i], Action.powCip(OsuProtocolParams.pk, taos.get(i), delta));
//                // 利用Ci集合重新更新数据库中的密文块
//                try {
//                    field.set(userInfo, String.valueOf(selectResult[i].getCp()));
//                } catch (IllegalAccessException e) {
//                    log.error("无权访问该字段的值。报错为{}", e.getMessage());
//                    return;
//                }
//                userInfoDao.save(userInfo);
//            } else {
//                log.info("用户：{}未被注册", accessSet.get(i));
//            }
//        }

        // 通过下标获取对应的用户数据密文
        indexAndTaoMap.entrySet().stream()
                .parallel()
                // 将hashmap转化为TaoAndCdata流
                .map(entry -> TaoAndCdata.builder().id(entry.getKey()).tao(entry.getValue()).cdata(null).build())
                // TaoAndCdata流依次进行处理
                .forEach(taoAndCdata -> {
                    Optional<UserInfo> userInfoOptional = userInfoDao.findById(taoAndCdata.getId());
                    if (userInfoOptional.isPresent()) {
                        Field field;
                        String targetColumnValue;
                        try {
                            UserInfo userInfo = userInfoOptional.get();
                            field = userInfo.getClass().getDeclaredField(targetColumn);
                            field.setAccessible(true);
                            targetColumnValue = (String) field.get(userInfo);
                        } catch (IllegalAccessException e) {
                            log.error("无权访问该字段的值。报错为{}", e.getMessage());
                            return;
                        } catch (NoSuchFieldException e) {
                            log.error("找不到该字段。报错为{}",e.getMessage());
                            return;
                        }
                        CipherText cDate = new CipherText();
                        cDate.setCp(new BigInteger(targetColumnValue));
                        ArrayList S = new ArrayList();
                        S.add(0, OsuProtocolParams.s+2);
                        cDate.setS(S);
                        cDate.setL(OsuProtocolParams.s);
                        taoAndCdata.setCdata(cDate);

                        // 利用delta对taos依次做同态乘，结果保存在taos中
                        // 利用数据块Ci和tao依次做同态加,结果保存在Ci集合中
                        taoAndCdata.getTao().setCp(taoAndCdata.getTao().getCp().mod(OsuProtocolParams.pk.getN().pow(OsuProtocolParams.s+2)));
                        CipherText result = Action.HomoAdd(OsuProtocolParams.pk, taoAndCdata.getCdata(), Action.powCip(OsuProtocolParams.pk, taoAndCdata.getTao(), delta));
                        // 利用Ci集合重新更新数据库中的密文块
                        try {
                            field.set(userInfoOptional.get(), String.valueOf(result.getCp()));
                        } catch (IllegalAccessException e) {
                            log.error("无权访问该字段的值。报错为{}", e.getMessage());
                            return;
                        }
                        userInfoDao.save(userInfoOptional.get());
                    }
                });
    }
}
