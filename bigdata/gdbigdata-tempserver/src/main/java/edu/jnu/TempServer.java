package edu.jnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 16:47
 */
@EnableOpenApi
@SpringBootApplication
public class TempServer {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(TempServer.class, args);
    }
}
