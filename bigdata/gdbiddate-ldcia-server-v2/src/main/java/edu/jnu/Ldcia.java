package edu.jnu;

import com.alibaba.fastjson.JSON;
import edu.jnu.entity.AuditParams;
import edu.jnu.entity.Challenge;
import edu.jnu.entity.IntegrityProof;
import edu.jnu.entity.User;
import edu.jnu.utils.AuditTool;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class Ldcia {
    public static void main( String[] args ) {
        SpringApplication.run(Ldcia.class, args);
    }
//        Pairing bp = PairingFactory.getPairing("a.properties");
//        // 获得G群
//        Field multiplicativeCyclicGroup = bp.getG1();
//        // 获得Zp群
//        Field groupOfIntegers = bp.getZr();
//        // 从G群中随机选择一个生成元g
//        Element g = multiplicativeCyclicGroup.newRandomElement();
//        // 从Zp群中随机选择一个生成元x
//        Element x = groupOfIntegers.newRandomElement();
//        // 获得g^x作为公钥
//        Element g_x = g.duplicate().powZn(x);
//
//        // 签名
//        String m = "sdcsdcsdvdfv";
//        // 将hashAndMap
//        byte[] m_hash = Integer.toString(m.hashCode()).getBytes();
//        Element h = multiplicativeCyclicGroup.newElementFromHash(m_hash, 0, m_hash.length);
//        // 签名
//        Element sign = h.duplicate().powZn(x);
//
//        // 左边
//        Element left = bp.pairing(g, sign);
//        Element right = bp.pairing(h, g_x);
//
//        if (right.isEqual(left)) {
//            System.out.println("匹配成功");
//        } else {
//            System.out.println("匹配失败");
//        }


        /*********************************************************************************************************************/


//        Path path = Paths.get("D:\\广东省大数据项目代码\\gdbigdata\\gdbiddate-ldcia-server-v2\\src\\main\\resources\\a.properties");
//
//        User user = new User();
//        List<List<String>> dataAndSignFile = user.sign(path);
//        List<String> dataList = dataAndSignFile.remove(0);
//        List<String> signList = dataAndSignFile.remove(0);
//
//        String fileAbstract = AuditTool.getFileAbstract(null);
//        List<Challenge> list = AuditTool.yieldChallenges(3, 3);
//        Element r = user.getR(fileAbstract);
//        Element h = user.getH(r);
//
//        Element h1 = user.getH1(h, user.getSk());
//        Element sign1 = user.sign(r, h, fileAbstract, list.get(0).getIndex(), dataList.get(0).getBytes());
//        Element sign2 = user.sign(r, h, fileAbstract, list.get(1).getIndex(), dataList.get(1).getBytes());
//        Element sign3 = user.sign(r, h, fileAbstract, list.get(2).getIndex(), dataList.get(2).getBytes());
//
////        Element dataSum = AuditTool.hashTwo(dataList.get(0)).mul(AuditTool.hashTwo(String.valueOf(list.get(0).getRandom())))
////                        .add(AuditTool.hashTwo(dataList.get(1)).mul(AuditTool.hashTwo(String.valueOf(list.get(1).getRandom())))
////                        .add(AuditTool.hashTwo(dataList.get(2)).mul(AuditTool.hashTwo(String.valueOf(list.get(2).getRandom())))));
//        Element dataSum = AuditTool.str2ZpElement(getDataAggregation(list, dataList));
//
////        Element signSum = sign1.powZn(AuditTool.hashTwo(list.get(0).getRandom().toString()))
////                        .mul(sign2.powZn(AuditTool.hashTwo(list.get(1).getRandom().toString())))
////                        .mul(sign3.powZn(AuditTool.hashTwo(list.get(2).getRandom().toString())));
//        Element signSum = AuditTool.str2G1Element(getSignAggregation(list, signList));
////        System.out.println(user.verify(user.getPk(), signSum, dataSum, fileAbstract, h, h1, list));
//
//        boolean verify = user.verify(signSum, dataSum, AuditParams.builder().h(AuditTool.g1Element2Str(h)).h1(AuditTool.g1Element2Str(h1)).build(), list);
//        System.out.println(verify);



        /*********************************************************************************************************************/

        /*********************************************************************************************************************/

//        Path path = Paths.get("D:\\广东省大数据项目代码\\gdbigdata\\gdbiddate-ldcia-server-v2\\src\\main\\java\\edu\\jnu\\Ldcia.java");
//
//        User user = new User();
//        List<List<String>> dataAndSignFile = user.sign(path);
//        List<String> dataList = dataAndSignFile.remove(0);
//        List<String> signList = dataAndSignFile.remove(0);
//
//
//
//        AuditParams auditParams = JSON.parseObject(signList.remove(signList.size() - 1), AuditParams.class);
//
//
//        List<Challenge> challenges = AuditTool.yieldChallenges(400, auditParams.getN());
//        System.out.println(challenges);
//
//        IntegrityProof proof = IntegrityProof.builder()
//                .auditParams(auditParams)
//                .signAggregation(getSignAggregation(challenges, signList))
//                .dataAggregation(getDataAggregation(challenges, dataList))
//                .build();
//
//        boolean verify = user.verify(AuditTool.str2G1Element(proof.getSignAggregation()), AuditTool.str2ZpElement(proof.getDataAggregation()), auditParams, challenges);
//
//        System.out.println(verify);
//    }
//
//    private static String getDataAggregation(List<Challenge> challenges, List<String> dataList) {
//        List<Element> list = challenges.stream()
//                .map(challenge -> AuditTool.hashTwo(dataList.get(challenge.getIndex())).mul(AuditTool.hashTwo(String.valueOf(challenge.getRandom()))))
//                .toList();
//        Element result = list.stream()
//                .reduce(AuditTool.getZpZero(), Element::add);
//        return AuditTool.zpElement2Str(result);
//    }
//
//    private static String getSignAggregation(List<Challenge> challenges, List<String> signList) {
//        // 先将字符串转会Element类型
//        List<Element> signs = new ArrayList<>(signList.stream()
//                .parallel()
//                .map(AuditTool::str2G1Element)
//                .toList());
//        // 先处理
//        List<Element> list = challenges.stream()
//                .parallel()
//                .map(challenge -> signs.get(challenge.getIndex()).powZn(AuditTool.hashTwo(challenge.getRandom().toString())))
//                .toList();
//        Element result = list.stream()
//                .reduce(AuditTool.getG1One(), Element::mul);
//        return AuditTool.g1Element2Str(result);
//    }
}
