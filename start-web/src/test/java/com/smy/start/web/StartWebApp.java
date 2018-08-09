package com.smy.start.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author smy
 */
@SpringBootApplication
public class StartWebApp {

    public static void main(String[] args) {
        //http://localhost:8080/swagger-ui.html
        //http://127.0.0.1:8080/actuator/health
        SpringApplication.run(StartWebApp.class);
    }
}
