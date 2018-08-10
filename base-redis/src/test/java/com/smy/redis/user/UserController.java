package com.smy.redis.user;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author smy
 */
@RestController
@RequestMapping("user")
@CacheConfig()
public class UserController {

    @Resource
    private UserService service;

    @PostMapping("add")
    public User add(User user) {
        return service.add(user);
    }

    @PostMapping("get")
    public User get(String id) {
        return service.get(id);
    }
}
