package com.mtlaa.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(value = {"com.mtlaa.mychat.transaction", "com.mtlaa.websocket"})
@EnableFeignClients(basePackages = "com.mtlaa.api.client")
public class WebsocketPushApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebsocketPushApplication.class, args);
    }
}
