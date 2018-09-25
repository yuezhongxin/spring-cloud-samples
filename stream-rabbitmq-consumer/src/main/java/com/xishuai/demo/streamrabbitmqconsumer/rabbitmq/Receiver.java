package com.xishuai.demo.streamrabbitmqconsumer.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(value = {MyInputChannel.class, Processor.class}) //绑定两个Binder
public class Receiver {

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    //响应消息
    @StreamListener(Processor.INPUT)
    public void receiveWithSink(Object payload) {

        logger.info("Received(input): " + payload);

    }

    //响应和回调发送消息
    @StreamListener(MyInputChannel.MY_INPUT_CHANNEL)
    @SendTo(MyInputChannel.MY_OUTPUT_CHANNEL_CALLBACK)
    public String receive(Object payload) {

        logger.info("Received(my_input_channel): " + payload);

        return "收到～";
    }
}
