package com.xishuai.demo.streamrabbitmqproducer.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyOutputChannel {

    String MY_OUTPUT_CHANNEL = "my_output_channel"; //发送output定义

    String MY_INPUT_CHANNEL_CALLBACK = "my_input_channel_callback"; //回调input定义

    @Output(MY_OUTPUT_CHANNEL)
    MessageChannel output();

    @Input(MY_INPUT_CHANNEL_CALLBACK)
    SubscribableChannel input();
}
