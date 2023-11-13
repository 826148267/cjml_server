package edu.jnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class AccessRealServer {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(AccessRealServer.class, args);
    }
}