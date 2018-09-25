package com.xishuai.demo.configclientgit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class Config2Controller {

    @Value("${info2.name2}")
    private String name;

    @Value("${info2.password2}")
    private String password;

    @RequestMapping("/name2")
    public String name() {
        //或者通过访问 http://worker2:8190/info2，获取info2下所有的配置信息
        return this.name;
    }

    @RequestMapping("/password2")
    public String password() {
        return this.password;
    }

}
