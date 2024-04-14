package edu.jnu.service;

import com.aliyun.oss.model.OSSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月04日 21:05
 */
@Service
public class SepdpProofService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SepdpProofService.class);

    @Autowired
    private OSSService ossService;

    /**
     * 获取被质询的数据块集合.
     * @param fileFullPath  文件的全路径
     * @param iList 质询集合
     * @return  数据块集合
     */
    public ArrayList<BigInteger> getMList(String fileFullPath, ArrayList<Integer> iList) {
        // 通过文件获取文件输入流
        OSSObject ossObject = ossService.getObj(fileFullPath, "gdbigdata");
        // 对iList进行排序
        Collections.sort(iList);
        // 依照iList的元素的值vi获取第vi行数据
        return getListFromPointedLinesArrayInInputStream(ossObject.getObjectContent(), iList);
    }

    public ArrayList<BigInteger> getSList(String fileFullPath, ArrayList<Integer> iList) {
        // 通过文件获取文件输入流
        OSSObject ossObject = ossService.getObj(fileFullPath, "gdbigdata");
        // 对iList进行排序
        Collections.sort(iList);
        // 依照iList的元素的值vi获取第vi行数据
        return getListFromPointedLinesArrayInInputStream(ossObject.getObjectContent(), iList);
    }

    /**
     * 通过行数数组从输入流中获取数据块集合.
     * @param inputStream 输入流
     * @param iList 列表中为数字集合，数字代表需要读取的行数
     * @return  返回数据块集合
     */
    private ArrayList<BigInteger> getListFromPointedLinesArrayInInputStream(InputStream inputStream, ArrayList<Integer> iList) {
        ArrayList<BigInteger> result = new ArrayList<>();   // 函数返回值
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String str = null;  // 当前行数据块
        int lineCounter = 0;   // 当前行指针
        try {
            // 初始化目标行号
            if (iList.size() < 1) {
                LOGGER.info("随机质询下标集合为空，无法产生证明");
                return null;
            }
            int index = 0;
            int lineNum = iList.get(index);

            while ((str = reader.readLine()) != null) { // 读取下一行
                // 如果目标行号等于当前行号
                if (((int) lineNum) == lineCounter) {
                    // 则取出该行数据
                    result.add(new BigInteger(str));
                    // 更新到下一个目标行号,
                    index ++;
                    if (index < iList.size()) {
                        lineNum = iList.get(index);
                    } else {    // 如果时最后一个目标行号，就结束读取输入流
                        break;
                    }
                }
                lineCounter ++; // 当前行指针后移1行
            }
        } catch (IOException e) {
            LOGGER.error("读取输入流时失败，原因为{}", e.getMessage());
        } finally {
            try {
                inputStreamReader.close();
                reader.close();
            } catch (IOException e) {
                LOGGER.error("关闭输入流时失败，原因为{}", e.getMessage());
            }
        }
        return result;
    }

    /**
     * 从"str1,str2,str3"类型的字符串中分割出大整数类型的数组
     * 然后根据下标数组选出新数组
     * @param originStr 源字符串
     * @param iList 下标数组
     * @return  返回新数组
     */
//    private ArrayList<BigInteger> getBigIntListInString(String originStr, ArrayList<Integer> iList) {
        // 以.分割字符串，然后将所以字符串都转化为大整数类型
//        String[] tmps = originStr.split(",");
//        ArrayList<BigInteger> results = new ArrayList<>();
//        for (int i = 0; i < iList.size(); i++) {
//            results.add(i, new BigInteger(tmps[iList.get(i)]));
//        }
//        return results;
//    }

    /**
     * γ = g^(R^(v_1*m_1+...+v_s*m_s)).
     * @param vList 随机数数组
     * @param mList 数据m数组
     * @return  返回gamma，用于完整性证明
     */
    public BigInteger getGamma(ArrayList<BigInteger> vList, ArrayList<BigInteger> mList) {
        BigInteger ma = getMultiplyAccumulate(vList, mList);
        BigInteger q = new BigInteger("107156293229077729250091877527205559797152387746091257873901795127714478829355151268192987540660538345541980278221919721227729188704880233489986953992401708029233425152676955790084204861352363073698986713212153973898552321306534386482266485959243979005986389570173096319404511570103341154639099929588845360061");
        BigInteger g = new BigInteger("67480145160642245730610966908890759954458566680508543923062777286992616368154012155930669347704235940523150168175801535785992220816518281617906551691534103230122389269615659745926725080666152793292320623358342609897553412452664898493878617859058425576319814485389864487490094559757162781025083913236570043411");
        return g.modPow(ma, q);
    }

    /**
     * 计算aList、bList内大整数的乘积累加.
     * @param aList a数组
     * @param bList b数组
     * @return  大整数乘积累加的值（大整数类型）
     */
    private BigInteger getMultiplyAccumulate(ArrayList<BigInteger> aList, ArrayList<BigInteger> bList) {
        int size = Math.min(aList.size(), bList.size());
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < size; i++) {
            result = result.add(aList.get(i).multiply(bList.get(i)));
        }
        return result;
    }

    /**
     * α = R^(v_1*s_1+...+v_s*s_s)
     * @param R 标签数组的最后一个大整数
     * @param vList 随机数数组
     * @param sList 标签数组
     * @return  返回α
     */
    public BigInteger getAlpha(BigInteger R, ArrayList<BigInteger> vList, ArrayList<BigInteger> sList) {
        BigInteger ma = getMultiplyAccumulate(vList, sList);
        BigInteger q = new BigInteger("107156293229077729250091877527205559797152387746091257873901795127714478829355151268192987540660538345541980278221919721227729188704880233489986953992401708029233425152676955790084204861352363073698986713212153973898552321306534386482266485959243979005986389570173096319404511570103341154639099929588845360061");
        return R.modPow(ma, q);
    }
}
