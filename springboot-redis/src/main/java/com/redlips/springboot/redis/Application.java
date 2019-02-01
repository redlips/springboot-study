package com.redlips.springboot.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qiaotong
 * @create 2019-01-25 14:56
 * @description
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
