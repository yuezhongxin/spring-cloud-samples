package com.xishuai.demo.eurekaconsumerhystrixwithfeign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloClientHystrix implements HelloClient {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello " + name + ", error by hystrix fallback";
    }
}
