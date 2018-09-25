package com.xishuai.demo.eurekaclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@RestController
public class HomeController {

    @Autowired
    DiscoveryClient discoveryClient;

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/home")
    public String home() {
        String services = "Services(get222222 all by DiscoveryClient): " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false) String name) {
        if (name == null){
            name = "world";
        }
        return "hello " + name;
    }

    @GetMapping("/user")
    public User getUser() {
        User user = new User();
        user.setId(1111);
        user.setUserName("world");
        return user;
    }

    @PostMapping("/post")
    public User post(@RequestBody User user) {
        user.setUserName("world");
        return user;
    }

    @GetMapping("/home-retry")
    public String homeRetry() {
        logger.info("request world with retry");
        try{
            Thread.sleep(1000000);
        }catch ( Exception e){
            logger.error(" hello error",e);
        }
        return "hello world with retry2";
    }

    @GetMapping("/home-error")
    public void homeError() throws Exception {
        throw new Exception("some error...");
    }

}
