package com.smy.redis.user;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author smy
 */
public class UserTest {

    @Test
    public void test1() throws Exception {
        String json = "{\n" +
                "  \"@class\": \"com.smy.redis.user.User\",\n" +
                "  \"id\": \"1\",\n" +
                "  \"name\": \"SK\",\n" +
                "  \"age\": 1\n" +
                "}";
        User user = (User) new GenericJackson2JsonRedisSerializer().deserialize(json.getBytes());
        System.out.println(user);
    }
}