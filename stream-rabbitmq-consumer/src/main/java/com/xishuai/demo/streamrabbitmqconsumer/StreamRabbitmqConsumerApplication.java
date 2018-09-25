package com.xishuai.demo.streamrabbitmqconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class StreamRabbitmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamRabbitmqConsumerApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {

		return "hello world!!!";
	}
}
