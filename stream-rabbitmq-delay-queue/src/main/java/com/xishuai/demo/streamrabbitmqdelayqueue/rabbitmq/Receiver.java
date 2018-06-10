package com.xishuai.demo.streamrabbitmqdelayqueue.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@EnableBinding(value = {MyChannelnput.class, MyChannelOutput.class})
public class Receiver {

    //public static CountDownLatch latch;
    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    //响应消息
    @StreamListener(MyChannelnput.INPUT)
    public void receiveWithSink(Object payload) throws Exception{

        logger.info("Received(input): " + payload);

        throw new Exception("Some exception happened");

        //logger.info("Received(input): " + payload);


        /*if (latch != null) {
            latch.countDown();
        }*/
    }
}
