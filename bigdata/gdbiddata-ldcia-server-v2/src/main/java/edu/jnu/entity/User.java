package edu.jnu.entity;

import com.alibaba.fastjson.JSONObject;
import edu.jnu.utils.AuditTool;
import it.unisa.dia.gas.jpbc.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月03日 17时29分
 * @功能描述: 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    // 私钥
    private Element sk;

    // 公钥
    private Element pk;

    public String getKeySeed() {
        return "myseed";
    }

    public Element getSk() {
        if (sk == null) {
            // 通过seed获得确定性的sk
            sk = AuditTool.hashTwo(this.getKeySeed());
        }
        return sk;
    }

    public Element getPk() {
        if (pk == null) {
            pk = AuditTool.g.duplicate().powZn(sk);
        }
        return pk;
    }

    public Element getR(String fa) {
        if (sk == null) {
            getSk();
        }
        return AuditTool.hashTwo(sk.toString()+fa);
    }

    public Element getH(Element r) {
        return AuditTool.g.duplicate().powZn(r);
    }

    public Element getH1(Element h, Element sk) {
        return h.duplicate().powZn(sk);
    }

    /**
     * 将文件进行签名
     * @param path 进行base64编码后的File实例的path
     * @return 返回两个List，第一个是按行分割的数据文件数组，第二个是按行分割的标签文件数组（最后一行是审计的参数AuditParams对象的json字符串）
     */
    public List<List<String>> sign(Path path) throws IOException {
        List<List<String>> result = new ArrayList<>();
        // 按行读出文件
        List<String> lines = Arrays.stream(
                Arrays.toString(
                        Base64.getEncoder().encode(Files.readAllBytes(path))
                ).split("(?<=\\G.{" + 10000 + "})")
        ).toList();
        result.add(lines);

        // 按行进行签名
        String fileAbstract =AuditTool.getFileAbstract(path);
        Element r = this.getR(fileAbstract);
        Element h = this.getH(r);
        // 为并行处理做准备
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            hashMap.put(i, lines.get(i));
        }
        // 并行进行签名
        List<String> signList = new ArrayList<>(
                hashMap.entrySet().stream()
                                    .parallel()
                                    .map(entry -> AuditTool.g1Element2Str(sign(r, h, fileAbstract, entry.getKey(), entry.getValue().getBytes())))
                                    .toList());
        Element h1 = this.getH1(h, sk);
        // 插入审计材料
        AuditParams auditParams = AuditParams.builder()
                .h(AuditTool.g1Element2Str(h))
                .h1(AuditTool.g1Element2Str(h1))
                .fa(fileAbstract)
                .n(signList.size())
                .build();
        signList.add(JSONObject.toJSONString(auditParams));
        result.add(signList);
        return result;
    }

    public Element sign(Element r, Element h, String fileAbstract, String index, byte[] mBytes) {
        Element left = AuditTool.hashOne(fileAbstract+index).powZn(r);
        Element low = AuditTool.u.duplicate().powZn(AuditTool.hashTwo(mBytes));
        Element high = (sk.duplicate().add(AuditTool.hashTwo(fileAbstract+h.toString()))).invert();
        return (low.powZn(high)).mul(left);
    }

    public Element sign(Element r, Element h, String fileAbstract, Integer index, byte[] mBytes) {
        return sign(r, h, fileAbstract, index.toString(), mBytes);
    }

    public Element sign(Element r, Element h, byte[] fileAbstractAndIndex, byte[] fileAbstractAndH, byte[] mBytes) {
        Element left = AuditTool.hashOne(fileAbstractAndIndex).powZn(r);
        Element low = AuditTool.u.duplicate().powZn(AuditTool.hashTwo(mBytes));
        Element high = sk.duplicate().add(AuditTool.hashTwo(fileAbstractAndH)).invert();
        return low.powZn(high).mulZn(left);
    }

    public boolean verify(Element pk, Element sign, Element data, String fileAbstract, Element h, Element h1, List<Challenge> challenges) {
        String fileAbstractAndH = fileAbstract + h.toString();
        Element value = getMutil(fileAbstract, challenges);
        Element left = AuditTool.BP.pairing(sign, pk.mul(AuditTool.g.duplicate().powZn(AuditTool.hashTwo(fileAbstractAndH))));
        Element right1 = AuditTool.BP.pairing(value, h1.mul(h.powZn(AuditTool.hashTwo(fileAbstractAndH))));
        Element right2 = AuditTool.BP.pairing(AuditTool.u.duplicate().powZn(data), AuditTool.g);
        Element right = right1.mul(right2);
        return left.equals(right);
    }

    public boolean verify(Element sign, Element data, AuditParams auditParams, List<Challenge> challenges) {
        Element pk = this.getPk();
        return this.verify(pk, sign, data, auditParams.getFa(), AuditTool.str2G1Element(auditParams.getH()), AuditTool.str2G1Element(auditParams.getH1()), challenges);
    }

    private Element getMutil(String fileAbstract, List<Challenge> challenges) {
        return challenges.stream()
                .map(challenge -> AuditTool.hashOne(fileAbstract+ challenge.getIndex()).powZn(AuditTool.hashTwo(String.valueOf(challenge.getRandom()))))
                .reduce(AuditTool.getG1One(), Element::mul);
    }
}
