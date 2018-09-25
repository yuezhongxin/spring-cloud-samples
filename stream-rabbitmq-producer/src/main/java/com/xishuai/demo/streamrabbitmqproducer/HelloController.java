package com.xishuai.demo.streamrabbitmqproducer;

import com.xishuai.demo.streamrabbitmqproducer.rabbitmq.MyOutputChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(value = {Processor.class, MyOutputChannel.class}) //绑定两个Binder
public class HelloController {

    @Autowired
    private Processor processor; //自带Processor，包含Sink和Source

    @Autowired
    private MyOutputChannel channel; //自定义MyOutputChannel

    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    //使用自带Processor的output方法，发送消息
    @GetMapping("/output")
    public String output() {
        processor.output().send(MessageBuilder.withPayload("二狗子在吗？").build());

        logger.info("二狗子在吗？");

        return "hello world!!!";
    }

    //使用自定义MyOutputChannel的output方法，发送消息
    @GetMapping("/output2")
    public String output2() {
        channel.output().send(MessageBuilder.withPayload("二狗子在吗？").build());

        logger.info("二狗子在吗？");

        return "hello world!!!";
    }

    @GetMapping("/hello")
    public String hello() {

        return "hello world!!!";
    }

    //上面output2发送消息后的回调消息
    @StreamListener(MyOutputChannel.MY_INPUT_CHANNEL_CALLBACK)
    public void receive(Object payload) {

        logger.info("Received(my_input_channel_callback): " + payload);

    }

}
