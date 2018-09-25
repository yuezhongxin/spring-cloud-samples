package com.xishuai.demo.eurekaconsumerhystrixwithfeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "eureka-client", fallback = HelloClientHystrix.class)
public interface HelloClient {

    @GetMapping("/hello")
    String hello(@RequestParam("name") String name);
}
