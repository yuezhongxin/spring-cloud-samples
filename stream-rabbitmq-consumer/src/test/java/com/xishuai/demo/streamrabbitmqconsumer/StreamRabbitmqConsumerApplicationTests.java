package com.xishuai.demo.streamrabbitmqconsumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StreamRabbitmqConsumerApplication.class)
@WebAppConfiguration
//@EnableBinding(value = {StreamRabbitmqConsumerApplicationTests.MyInputChannel.class})
public class StreamRabbitmqConsumerApplicationTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private Sink sink;

	/*@Autowired
	private MyInputChannel myOutputChannel;*/

	@Test
	public void contextLoads() {
		sink.input().send(MessageBuilder.withPayload("hello world 1").build());
	}

	/*@Test
	public void contextLoads2() {
		SendingBean service = thisgetClass(SendingBean.class);
		myOutputChannel.output().send(MessageBuilder.withPayload("hello world 1").build());
	}*/

}

