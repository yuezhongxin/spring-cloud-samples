package com.xishuai.demo.eurekaconsumerhystrixwithribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    HelloClient helloClient;

    @GetMapping("/consumer")
    public String home(@RequestParam(value = "name", required = false) String name) {
        return helloClient.hello(name) + " --------- by Ribbon(包含熔断处理)";
    }

    @GetMapping("/home-error")
    public String homeError() {
        return helloClient.homeError() + " --------- by Ribbon(包含熔断处理)";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world!!!";
    }

}
