package edu.jnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年05月12日 20:22
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka17000 {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(Eureka17000.class, args);
    }
}
