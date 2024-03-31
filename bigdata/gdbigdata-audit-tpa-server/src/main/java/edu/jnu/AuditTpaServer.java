package edu.jnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年05月08日 10:32
 */
@SpringBootApplication
public class AuditTpaServer {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(AuditTpaServer.class, args);
    }
}
