package edu.jnu.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 19:10
 */
@Service
public class OSSService {

    @Autowired
    private OSS oss;

    /**
     * 上传文件到OSS对象存储.
     * 将数据对象上传到OSS对象存储中.
     * @param objectName
     * @param bucketName
     * @param inputStream
     */
    public void uploadObjAsPlainTxt2OSS(String objectName, String bucketName, InputStream inputStream) {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        // 一律上传普通文本格式文件。
        metadata.setContentType("text/plain");
        putObjectRequest.setMetadata(metadata);
        // 上传文件。
        oss.putObject(putObjectRequest);
    }

    /**
     * 下载存储对象.
     * 从OSS对象存储中下载已有的数据对象.
     * @param objName
     * @param bucketName
     * @return
     */
    public OSSObject getObj(String objName, String bucketName) {
        return oss.getObject(new GetObjectRequest(bucketName, objName));
    }

    /**
     * 以字符串的形式读取OSSObject对象.
     * @param objName
     * @param bucketName
     * @return
     * @throws IOException
     */
    public String getStringInOssObject(String objName, String bucketName) throws IOException {
        OSSObject oo = oss.getObject(new GetObjectRequest(bucketName, objName));
        InputStream content = oo.getObjectContent();
        StringBuilder sb = new StringBuilder("");
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                sb.append(line);
            }
            //数据读取完成后，获取的流一定要显示close，否则会造成资源泄露。
            content.close();
        }
        return sb.toString();
    }

    public void deleteOssObject(String objName, String bucketName) {
        oss.deleteObject(bucketName, objName);
    }
}
