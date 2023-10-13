package edu.jnu.service;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月12日 21:51
 */
public class OSSFactory {
    public static OSS create(String endpoint, String accessKeyId, String accessKeySecret) {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
//        conf.setDefaultHeaders(); 可以通过conf进行设置
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
        return oss;
    }
}
