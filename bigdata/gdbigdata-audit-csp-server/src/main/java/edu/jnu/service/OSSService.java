package edu.jnu.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import edu.jnu.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 19:10
 */
@Service
public class OSSService {

//    /**
//     * 获得OSS对象存储的客户端对象.
//     * @param endpoint
//     * @param accessKeyId
//     * @param accessKeySecret
//     * @return
//     */
//    public OSS createOss(String endpoint, String accessKeyId, String accessKeySecret) {
//        return OSSFactory.create(endpoint, accessKeyId, accessKeySecret);
//    }

    @Autowired
    private OSS oss;

    /**
     * 上传文件到OSS对象存储.
     * 将数据对象上传到OSS对象存储中.
     * @param objectName
     * @param bucketName
     * @param inputStream
     */
    public void uploadObj2OSS(String objectName, String bucketName, InputStream inputStream) {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//        ObjectMetadata metadata = new ObjectMetadata();
        // 根据文件后缀来决定应该上传什么格式的数据。
//        metadata.setContentType(Tools.getContentType(objectName));
//        putObjectRequest.setMetadata(metadata);
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
     * 删除文件对象
     * @param objName   文件对象名
     * @param bucketName    bucket名
     */
    public void deleteObj(String objName, String bucketName) {
        oss.deleteObject(bucketName, objName);
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
}
