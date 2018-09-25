package com.xishuai.demo.zuulapigateway;

import com.xishuai.demo.zuulapigateway.filter.AccessTokenFilter;
import com.xishuai.demo.zuulapigateway.filter.CustomErrorFilter;
import com.xishuai.demo.zuulapigateway.filter.CustomResponseFilter;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableSwagger2Doc
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
//@EnableWebMvc
public class ZuulApiGatewayApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(ZuulApiGatewayApplication.class, args);

		//DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
		//dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}

	/*@Bean
	public AccessTokenFilter accessFilter() {
		return new AccessTokenFilter();
	}*/

	/*@Bean
	public CustomResponseFilter customResponseFilter() {
		return new CustomResponseFilter();
	}*/

	//Method过滤器，过滤请求URL中包含Method
	/*@Bean
	public MethodFilter methodFilter() {
		return new MethodFilter();
	}*/

    /*@Bean
    public CustomErrorFilter customErrorFilter() {
        return new CustomErrorFilter();
    }*/

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
