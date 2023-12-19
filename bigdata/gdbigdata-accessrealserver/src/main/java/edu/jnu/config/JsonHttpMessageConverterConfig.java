package edu.jnu.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.nio.charset.Charset;

/**
 * fastjson通用配置.
 * 可将@ResponseBody利用fastjson自动转化为json对象、
 * 将接口返回给客户端的java对象利用fastjson自动转化为json格式.
 * @author Guo zifan
 * @date 2022年01月24日 19:46
 */
@Configuration
public class JsonHttpMessageConverterConfig {
    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConvertes() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 最后一个配置SerializerFeature.DisableCircularReferenceDetect是用来关闭循环/重复引用的
        // 原因: 因为当两个对象相互引用时，当序列化后的JSON传输到浏览器或者其他语言中，
        //      这些json解析器不支持循环引用，从而导致数据丢失。
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.MapSortField,
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastJsonHttpMessageConverter);
    }
}
