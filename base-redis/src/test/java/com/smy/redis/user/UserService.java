package com.smy.redis.user;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author smy
 */
@Service
public class UserService {


    public User add(User user) {
        return user;
    }


    public User get(String id) {
        return null;
    }
}
