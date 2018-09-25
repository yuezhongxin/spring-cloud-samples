package com.xishuai.demo.authorizationserveroauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.xishuai.demo.authorizationserveroauth2.repository")
public class AuthorizationServerOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerOauth2Application.class, args);
    }
}
