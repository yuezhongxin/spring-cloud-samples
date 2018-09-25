package com.xishuai.demo.eurekaconsumerhystrixwithribbon;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloClient {

    @Autowired
    RestTemplate restTemplate;

    /*@HystrixCommand(groupKey = "PoiInfoServiceCommand", commandKey="getStagedPoiBase", fallbackMethod = "helloServiceFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),//指定多久超时，单位毫秒。超时进fallback
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds" , value = "10000") //熔断多少毫秒后开始尝试请求 默认5000ms
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"), //注解参考：http://tech.lede.com/2017/06/15/rd/server/hystrix/
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            }
    )*/
    @HystrixCommand(fallbackMethod = "helloServiceFallback")
    public String hello(String name) {
        return restTemplate.getForObject("http://eureka-client/hello?name=" + name, String.class);
    }

    @HystrixCommand(fallbackMethod = "homeErrorServiceFallback")
    public String homeError() {
        return restTemplate.getForObject("http://eureka-client/home-error", String.class);
    }

    public String helloServiceFallback(String name) {
        return "hello " + name + "; error by hystrix fallback";
    }

    public String homeErrorServiceFallback() {
        return "home error by hystrix fallback";
    }

}
