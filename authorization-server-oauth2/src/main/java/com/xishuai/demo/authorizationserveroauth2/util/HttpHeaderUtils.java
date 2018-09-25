package com.xishuai.demo.authorizationserveroauth2.util;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by yuezhongxin on 2018/9/22.
 */
public class HttpHeaderUtils {
    public static final String DEVICE = "X-Device-Name";
    public static final String DEFAULT_DEVICE = "webpc";

    public HttpHeaderUtils() {
    }

    public static HttpHeaders getHeader(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration headerNames = request.getHeaderNames();

        while(headerNames.hasMoreElements()) {
            String key = (String)headerNames.nextElement();
            String value = request.getHeader(key);
            headers.set(key, value);
        }

        return headers;
    }
}
