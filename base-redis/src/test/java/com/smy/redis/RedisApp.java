package com.smy.redis;

import com.smy.redis.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

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
