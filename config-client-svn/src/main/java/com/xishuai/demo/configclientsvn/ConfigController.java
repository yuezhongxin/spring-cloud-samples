package com.xishuai.demo.configclientsvn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigController {

    @Value("${info.name}")
    private String name;

    @Value("${info.password}")
    private String password;

    @RequestMapping("/name")
    public String name() {
        //或者通过访问 http://worker2:8135/info，获取info下所有的配置信息
        return this.name;
    }

    @RequestMapping("/password")
    public String password() {
        return this.password;
    }

}
