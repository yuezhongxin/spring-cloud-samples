package com.xishuai.demo.zuulapigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * 代码完全拷贝自：SendErrorFilter（源码）
 * 需要注意：error类型的过滤器，只能捕获pre、route和post过滤器执行过程中的错误，并不能捕获服务运行的错误。
 */
public class CustomErrorFilter extends ZuulFilter {

    public CustomErrorFilter() {
    }

    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        Throwable throwable = context.getThrowable();

        StringBuilder data = new StringBuilder();
        data.append("{\"code\":").append(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        data.append(", \"msg\":");
        data.append("\"").append(throwable.getCause().getMessage()).append("\"");
        data.append("}");

        try {
            context.getResponse().setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(data.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            rethrowRuntimeException(e);
        }
        context.set("isSuccess", false);
        return null;
    }

}
