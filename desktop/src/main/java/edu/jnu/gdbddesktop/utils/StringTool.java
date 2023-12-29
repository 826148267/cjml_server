package edu.jnu.gdbddesktop.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月04日 14时52分
 * @功能描述: TODO
 */
public class StringTool {

    /**
     * 将byte[]格式的字符串转化为byte[]
     *
     * @param string 需要转换的字符串
     * @return 转换后的byte[]
     */
    public static byte[] stringToBytes(String string) {
        // 去除首尾[与]两个符号
        string = string.substring(1, string.length()-1);
        // 将字符串分割成数组
        String[] strings = string.split(",");
        List<Byte> byteList = Arrays.stream(strings)
                .map(s -> Byte.parseByte(s.trim()))
                .toList();
        byte[] bytes = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            bytes[i] = byteList.get(i);
        }
        return bytes;
    }

    public static String bytesToString(byte[] bytes) {
        return Arrays.toString(bytes);
    }

    public static List<String> strToList(String s, int length) {
        if (null == s || "".equals(s)) {
            return null;
        }
        if (length <= 0) {
            return null;
        }
        int n = (s.length() + length - 1)/length;
        List<String> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            if (i<(n-1)) {
                list.add(s.substring(i*length, (i+1)*length));
            } else {
                list.add(s.substring(i*length));
            }
        }
        return list;
    }
}
