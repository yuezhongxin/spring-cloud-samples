package com.xishuai.demo.eurekaconsumerfeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("eureka-client")
public interface HelloClient {

    @GetMapping("/hello")
    String hello(@RequestParam("name") String name);

    @PostMapping("/post")
    String post(@RequestBody User user);
}
