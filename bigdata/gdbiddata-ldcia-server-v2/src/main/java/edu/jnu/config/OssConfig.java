package edu.jnu.config;

import com.aliyun.oss.OSS;
import edu.jnu.service.OSSFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月05日 13:10
 */
@Configuration
public class OssConfig {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Bean
    public OSS initOss() {
        OSS oss = OSSFactory.create(endpoint, accessKeyId, accessKeySecret);
        return oss;
    }
}
