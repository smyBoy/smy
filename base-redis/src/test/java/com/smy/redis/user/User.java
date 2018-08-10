package com.smy.redis.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author smy
 */
@Getter
@Setter
@ToString
public class User {
    private String id;
    private String name;
    private int age;
}
