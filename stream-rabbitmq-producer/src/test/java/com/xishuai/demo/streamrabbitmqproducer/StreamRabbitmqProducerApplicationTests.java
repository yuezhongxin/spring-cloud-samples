package com.xishuai.demo.streamrabbitmqproducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StreamRabbitmqProducerApplication.class)
@WebAppConfiguration
public class StreamRabbitmqProducerApplicationTests {

	@Autowired
	private Sink sink;

	@Test
	public void contextLoads() {
		sink.input().send(MessageBuilder.withPayload("hello world 1").build());
	}

}
