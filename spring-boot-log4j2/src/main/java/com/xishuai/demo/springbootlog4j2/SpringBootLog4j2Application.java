package com.xishuai.demo.springbootlog4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootLog4j2Application implements ApplicationRunner {

	private static final Logger logger = LogManager.getLogger(SpringBootLog4j2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLog4j2Application.class, args);
	}

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		logger.debug("Debugging log");
		logger.info("Info log");
		logger.warn("Hey, This is a warning!");
		logger.error("jack! We have an Error. OK");
		logger.fatal("xishuai! Fatal error. Please fix me.");
	}
}
