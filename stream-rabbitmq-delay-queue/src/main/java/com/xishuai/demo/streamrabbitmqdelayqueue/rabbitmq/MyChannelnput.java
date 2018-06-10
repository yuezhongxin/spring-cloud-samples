package com.xishuai.demo.streamrabbitmqdelayqueue.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MyChannelnput {
    String INPUT = "mychannel_input";

    @Input(INPUT)
    SubscribableChannel input();
}
