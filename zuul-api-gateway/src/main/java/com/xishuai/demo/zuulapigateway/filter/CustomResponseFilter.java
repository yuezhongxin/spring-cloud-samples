package com.xishuai.demo.zuulapigateway.filter;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * 定义服务统一返回格式
 */
public class CustomResponseFilter extends ZuulFilter {

    public static final String OCCURRED_ERROR_IN_HEADER = "X-Occurred-Error";

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return (boolean) context.get("isSuccess");
    }

    @Override
    public Object run() {

        //参考：https://www.programcreek.com/java-api-examples/?class=com.netflix.zuul.context.RequestContext&method=getResponseDataStream
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();

        try {
            //从Respone Header中判断是否是异常数据
            //参考：https://github.com/Netflix/zuul/issues/326
            Boolean occurredError = false;
            List<Pair<String, String>> zuulResponseHeaders = context.getZuulResponseHeaders();
            if (zuulResponseHeaders != null) {
                for (Pair<String, String> it : zuulResponseHeaders) {
                    if (it.first().contains(OCCURRED_ERROR_IN_HEADER)) {
                        occurredError = true;
                        break;
                    }
                }
            }

            InputStream responseDataStream = context.getResponseDataStream();
            String responseAsString = StreamUtils.copyToString(responseDataStream, StandardCharsets.UTF_8);
            String responeBody = "";
            if (occurredError) {
                responeBody = responseAsString;
            } else {
                StringBuilder data = new StringBuilder();
                data.append("{\"code\":0, \"data\":");
                if (responseAsString.startsWith("{") && responseAsString.endsWith("}")) {
                    data.append(responseAsString);
                } else {
                    data.append("\"").append(responseAsString).append("\"");
                }
                data.append("}");
                responeBody = data.toString();
                //context.setResponseDataStream(new ByteArrayInputStream(body.toString().getBytes("UTF-8")));
            }

            //参考：https://www.programcreek.com/java-api-examples/?class=com.netflix.zuul.context.RequestContext&method=getResponseDataStream
            context.getResponse().setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(responeBody);
            out.flush();
            out.close();
        } catch (IOException e) {
            rethrowRuntimeException(e);
        }

        return null;
    }
}
