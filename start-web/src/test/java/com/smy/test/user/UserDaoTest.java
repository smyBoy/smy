package com.smy.test.user;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by smy on 2018/7/24.
 */
@SpringBootTest
public class UserDaoTest extends AbstractTestNGSpringContextTests{

    @Resource
    private UserDao userDao;

    @Test
    public void test1() throws Exception {
        List<User> users = userDao.test1("张三", "");
    }
}