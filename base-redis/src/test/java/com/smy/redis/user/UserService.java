package com.smy.redis.user;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author smy
 */
@Service
public class UserService {

    @CachePut(value = "user", key = "#user.id")
    public User add(User user) {
        return user;
    }

    @Cacheable(value = "user", key = "#id")
    public User get(String id) {
        return null;
    }
}
