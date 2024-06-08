package edu.jnu.gdbddesktop.utils;

import edu.jnu.gdbddesktop.entity.Challenge;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月03日 17时10分
 * @功能描述: 审计用工具
 */
public class AuditTool {
    public static final Pairing BP = PairingFactory.getPairing("a.properties");
    private static final Field G = BP.getG1();

    private static final Field Z_p = BP.getZr();
    public static final Element u = G.newElementFromHash("u".getBytes(), 0, "u".getBytes().length);
    public static final Element g = G.newElementFromHash("u".getBytes(), 0, "u".getBytes().length);

    /**
     * @param m m为需要映射到G1群的字符串
     * @return 返回一个G1群上的Element
     */
    public static Element hashAndMap(String m) {
        byte[] mHash = Integer.toString(m.hashCode()).getBytes();
        return G.newElementFromHash(mHash, 0, mHash.length);
    }

    /**
     * @param mHash 此处的mHash应该使用m.hashCode()
     * @return 返回一个G1群上的Element
     */
    public static Element hashAndMap(byte[] mHash) {
        return G.newElementFromHash(mHash, 0, mHash.length);
    }

    /**
     * H1函数
     * 将byte数组映射成G群上的元素
     * @param bytes 字节数组
     * @return 返回一个hash后的元素
     */
    public static Element hashOne(byte[] bytes) {
        return hashAndMap(bytes);
    }

    public static Element hashOne(String str) {
        return hashOne(str.getBytes());
    }

    /**
     * H2函数.
     * 将byte数组映射成整数群Z_p群上的元素
     * @param bytes 字节数组
     * @return 返回一个hash后的元素
     */
    public static Element hashTwo(byte[] bytes) {
        return Z_p.newElementFromHash(bytes, 0, bytes.length);
    }

    public static Element hashTwo(String str) {
        byte[] bytes = str.getBytes();
        return Z_p.newElementFromHash(bytes, 0, bytes.length);
    }

    public static String getFileAbstract(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            MessageDigest md5 = MessageDigest.getInstance( "MD5");
            byte[] digested = md5.digest(bytes);
            return Base64.getEncoder().encodeToString(digested);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("java库中无法识别出MD5算法");
        }
        return "null";
    }

    public static String getFileAbstract(String content) {
        try {
            MessageDigest md5 = MessageDigest.getInstance( "MD5");
            byte[] digested = md5.digest(content.getBytes());
            return Base64.getEncoder().encodeToString(digested);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("java库中无法识别出MD5算法");
        }
        return "null";
    }

    public static Element getG1One() {
        return G.newOneElement();
    }

    public static Element getZpZero() {
        return Z_p.newZeroElement();
    }

    public static Element str2G1Element(String str) {
        return G.newElementFromBytes(StringTool.stringToBytes(str));
    }

    public static String g1Element2Str(Element element) {
        return StringTool.bytesToString(element.toCanonicalRepresentation());
    }

    public static List<Challenge> yieldChallenges(int i, int n) {
        List<Challenge> challenges = new ArrayList<>();
        if (n <= 0) {
            return challenges;
        }
        if (i > n) {
            i = n-1;
        }
        while (challenges.size() < i) {
            // 产生一个[0,n)的随机整数
            int r = (int)(Math.random() * n);
            // 产生一个[0,10000)的随机数
            int s = (int)(Math.random() * 10000);
            // 判断challenges的index属性是否存在r
            boolean exist = false;
            for (Challenge challenge: challenges) {
                if (challenge.getIndex() == r) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                challenges.add(new Challenge(r, s));
            }
        }
        // 将challenges列表按照challenge的index属性的值排序
        challenges.sort(Comparator.comparingInt(Challenge::getIndex));
        return challenges;
    }

    public static String zpElement2Str(Element element) {
        return StringTool.bytesToString(element.toCanonicalRepresentation());
    }

    public static Element str2ZpElement(String str) {
        return Z_p.newElementFromBytes(StringTool.stringToBytes(str));
    }

    /**
     * 从Zp群中获取一个随机元素
     * @return 随机元素
     */
    public static Element getRandomZpElement() {
        return Z_p.newRandomElement();
    }
}
