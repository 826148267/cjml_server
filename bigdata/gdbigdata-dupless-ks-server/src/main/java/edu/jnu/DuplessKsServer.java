package edu.jnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月12日 19:10
 */
@SpringBootApplication
public class DuplessKsServer {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(DuplessKsServer.class, args);
    }
}
