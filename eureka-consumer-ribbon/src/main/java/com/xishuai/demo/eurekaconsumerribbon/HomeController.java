package com.xishuai.demo.eurekaconsumerribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer")
    public String home() {
        return restTemplate.getForObject("http://eureka-client/hello", String.class) + " --------- by Ribbon(get http://eureka-client/home)";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world!!!";
    }

}
