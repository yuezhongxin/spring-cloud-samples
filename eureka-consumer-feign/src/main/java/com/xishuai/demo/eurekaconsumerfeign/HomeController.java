package com.xishuai.demo.eurekaconsumerfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    HelloClient helloClient;

    @GetMapping("/consumer")
    public String home(@RequestParam(value = "name", required = false) String name) {
        return helloClient.hello(name) + " --------- by Feign(基于声明式接口)";
    }

    @PostMapping("/consumer/post")
    public String home(@RequestBody User user) {
        return helloClient.post(user) + " --------- by Feign(基于声明式接口)";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world!!!";
    }

}
