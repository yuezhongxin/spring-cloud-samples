package com.xishuai.demo.streamrabbitmqdelayqueue;

import com.xishuai.demo.streamrabbitmqdelayqueue.rabbitmq.MyChannelOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.integration.support.MessageBuilder;

@RunWith(SpringRunner.class)
@EnableBinding(value = {MyChannelOutput.class})
@SpringBootTest
public class StreamRabbitmqDelayQueueApplicationTests {

	@Autowired
	private MyChannelOutput myChannelOutput;

	@Test
	public void testDelayQueuePerQueueTTL() throws InterruptedException{

		myChannelOutput.output().send(MessageBuilder.withPayload("hello 123").build());

		/*Receiver.latch = new CountDownLatch(3);
		for (int i = 1; i <= 3; i++) {
			myChannelOutput.output().send(MessageBuilder.withPayload("hello 123").build());
		}
		Receiver.latch.await();*/

	}

}
