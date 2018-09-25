package com.xishuai.demo.zuulapigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer")
    public String home() {
        return restTemplate.getForObject("http://localhost:8110/home", String.class);
    }

    @GetMapping("/local")
    public String hello() {

        return "hello api gateway!!!";
    }
}
