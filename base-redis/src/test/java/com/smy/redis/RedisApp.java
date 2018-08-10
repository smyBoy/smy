package com.smy.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author smy
 */
@SpringBootApplication
@EnableCaching
public class RedisApp {

    public static void main(String[] args) {
        //http://localhost:8080/swagger-ui.html
        SpringApplication.run(RedisApp.class);
    }
}
