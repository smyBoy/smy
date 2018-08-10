package com.smy.redis;

import com.smy.redis.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author smy
 */
@RestController
@RequestMapping("demo")
public class RedisDemo {
    @Autowired
    private RedisTemplate<String, Object> demoTemplate;

    @Autowired
    private RedisTemplate<String, User> userTemplate;

    @GetMapping("set")
    public Boolean set(String key, String value, Long ttl) {
        demoTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
        return true;
    }

    @GetMapping("get")
    public Object get(String key) {
        return demoTemplate.opsForValue().get(key);
    }

    @PostMapping("user")
    public Boolean putUser(User user) {
        userTemplate.opsForValue().set(user.getId(), user);
        return true;
    }

    @GetMapping("user")
    public User getUser(String id) {
        return userTemplate.opsForValue().get(id);
    }
}
