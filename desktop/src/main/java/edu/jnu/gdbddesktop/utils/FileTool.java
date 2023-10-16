package edu.jnu.gdbddesktop.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月05日 20时24分
 * @功能描述: 文件处理工具
 */
public class FileTool {

    /**
     * 1、将文件以byte[]形式一次性全部读出
     * 2、将byte[]转化为base64形式的byte[]
     * 3、将base64形式的byte[]转化为字符串
     * 4、将字符串按照10000个字符为单位切割成List
     * @param file 文件
     * @return 文件内容
     */
    public static List<String> readFileAsList(File file) {
        // 按行读出文件
        try {
            return StringTool.strToList(new String(
                            Base64.getEncoder().encode(Files.readAllBytes(file.toPath()))
                    ), 1000);
        } catch (IOException e) {
            System.out.println("文件读取失败："+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
