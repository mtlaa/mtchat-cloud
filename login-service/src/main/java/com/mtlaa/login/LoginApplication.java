package com.mtlaa.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"com.mtlaa.mtchat", "com.mtlaa.login"})
@SpringBootApplication
@EnableFeignClients(basePackages = "com.mtlaa.api.client")
public class LoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }
}
