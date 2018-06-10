package com.xishuai.demo.streamrabbitmqdelayqueue.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyChannelOutput {
    String OUTPUT = "mychannel_output";

    @Output(OUTPUT)
    MessageChannel output();
}
