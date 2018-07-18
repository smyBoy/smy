package com.smy.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by smy on 2018/7/19.
 */
@SpringBootApplication
public class TestApp {

    public static void main(String[] args) {
        //http://localhost:8080/swagger-ui.html
        //http://localhost:8080/druid
        //http://localhost:8080/actuator/health
        SpringApplication.run(TestApp.class);
    }
}
