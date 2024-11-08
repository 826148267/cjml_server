package edu.jnu.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import edu.jnu.utils.Tools;
import org.springframework.stereotype.Service;
import java.io.InputStream;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 19:10
 */
@Service
public class OSSService {

    /**
     * 获得OSS对象存储的客户端对象.
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     */
    public OSS createOss(String endpoint, String accessKeyId, String accessKeySecret) {
        return OSSFactory.create(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 上传文件到OSS对象存储.
     * 将数据对象上传到OSS对象存储中.
     * @param oss
     * @param objectName
     * @param bucketName
     * @param inputStream
     */
    public void uploadObj2OSS(OSS oss, String objectName, String bucketName, InputStream inputStream) {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        // 根据文件后缀来决定应该上传什么格式的数据。
        metadata.setContentType(Tools.getContentType(objectName));
        putObjectRequest.setMetadata(metadata);
        // 上传文件。
        oss.putObject(putObjectRequest);
    }

    /**
     * 下载存储对象.
     * 从OSS对象存储中下载已有的数据对象.
     * @param oss
     * @param objName
     * @param bucketName
     * @return
     */
    public InputStream downloadObj(OSS oss, String objName, String bucketName) {
        OSSObject result = oss.getObject(new GetObjectRequest(bucketName, objName));
        return result.getObjectContent();
    }
}
