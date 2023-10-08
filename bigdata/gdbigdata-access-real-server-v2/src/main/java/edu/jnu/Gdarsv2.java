package edu.jnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年04月26日 16时56分
 * @功能描述: 程序运行启动类
 */
@SpringBootApplication
@EnableOpenApi
public class Gdarsv2 {
    public static void main(String[] args) {
        SpringApplication.run(Gdarsv2.class, args);
    }
}
