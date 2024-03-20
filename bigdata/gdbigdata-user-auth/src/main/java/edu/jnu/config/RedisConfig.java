package edu.jnu.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类.
 * 作用：使用java存储数据时，redis默认的使用jdk的序列化方式，对运维不是很友好，我们转化为fastjson的格式.
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月14日 13:15
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置所有序列化都用fastjson
        redisTemplate.setDefaultSerializer(new FastJsonRedisSerializer<>(Object.class));
        // key的序列化单独设置，用来序列化String
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
