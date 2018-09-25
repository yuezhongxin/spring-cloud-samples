package com.xishuai.demo.zuulapigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 从Config配置中心，获取路由授权配置
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "authorization-router")
public class AuthorizationRouterConfig {

    private final Map<String, Map<String, AuthorizationRouter>> map = new HashMap<>();

    public Map<String, Map<String, AuthorizationRouter>> getMap() {
        return this.map;
    }

}
