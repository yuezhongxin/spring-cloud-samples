package com.xishuai.demo.streamrabbitmqconsumer.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyInputChannel {

    String MY_INPUT_CHANNEL = "my_input_channel"; //响应input定义

    String MY_OUTPUT_CHANNEL_CALLBACK = "my_output_channel_callback"; //发送回调output定义

    @Input(MY_INPUT_CHANNEL)
    SubscribableChannel input();

    @Output(MY_OUTPUT_CHANNEL_CALLBACK)
    MessageChannel output();
}
