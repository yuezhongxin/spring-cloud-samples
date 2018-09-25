package com.xishuai.demo.zuulapigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class MethodFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MethodFilter.class);

    @Override
    public String filterType() {
        return "pre";   //pre代表会在请求被路由之前执行
    }

    @Override
    public int filterOrder() {
        return 1;   //过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行。
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.get("isSuccess");
        // 如果前一个过滤器的结果为true，则说明上一个过滤器成功了，需要进入当前的过滤，
        // 如果前一个过滤器的结果为false，则说明上一个过滤器没有成功，则无需进行下面的过滤动作了，直接跳过后面的所有过滤器并返回结果
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        Object accessToken = request.getParameter("method");
        if(accessToken == null) {
            log.warn("method is empty");
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.setResponseBody("{\"result\":\"method is empty!\"}");// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }
        log.info("access token ok");
        ctx.set("isSuccess", true);
        return null;
    }

}
