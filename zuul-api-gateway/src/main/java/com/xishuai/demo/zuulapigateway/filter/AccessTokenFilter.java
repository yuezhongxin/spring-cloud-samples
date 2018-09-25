package com.xishuai.demo.zuulapigateway.filter;

import com.alibaba.fastjson.JSON;
import com.xishuai.demo.zuulapigateway.config.AuthorizationRouter;
import com.xishuai.demo.zuulapigateway.config.AuthorizationRouterConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xishuai.demo.zuulapigateway.util.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

public class AccessTokenFilter extends ZuulFilter {

    //public static final String API_PREFIX = "/api/";
    public static final String USER_NAME_IN_HEADER = "X-XISHUAI-USERNAME";
    public static final String USER_NAME_IN_JWT = "user_name";
    public static final String AUTHORITIES_IN_JWT = "authorities";
    public static final String SWAGGER_URL = "/v2/api-docs";
    /*private static final Map ROUTE_MAPS;
    static
    {
        ROUTE_MAPS = new HashMap<String, String>();
        ROUTE_MAPS.put("eureka-client/home", "read:ROLE_ADMIN");
        ROUTE_MAPS.put("eureka-client/user", "read:ROLE_ADMIN");
        ROUTE_MAPS.put("eureka-client/error", "read:ROLE_ADMIN");
    }*/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private AuthorizationRouterConfig authorizationRouterConfig;

    /**
     * pre代表会在请求被路由之前执行
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行。
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断该过滤器是否需要被执行
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        //从配置中心读取路由授权配置
        Map<String, Map<String, AuthorizationRouter>> routerMap = authorizationRouterConfig.getMap();
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        //获取url，并分割路由
        String url = request.getRequestURI().toString();
        String[] urls = url.split("/");
        //忽略swagger，以及不需要它授权验证的url
        if (url.contains(SWAGGER_URL) || urls.length < 3) {
            setSuccessResponse(context);
            return null;
        }

        //判断路由授权中是否包含当前请求url，如果不包含，返回错误
        String serviceName = urls[1];
        String router = url.replace("/" + serviceName + "/","");
        if (!routerMap.containsKey(serviceName) || !routerMap.get(serviceName).containsKey(router)){
            setErrorResponse(context,401,"未授权!");
            return null;
        }

        //获取token
        String authorization = request.getHeader("Authorization");
        //判断是不是符合规范的头部
        if (StringUtils.isNotEmpty(authorization)) {
            if (isJwtBearerToken(authorization)) {

                try {
                    //通过tokenServices读取OAuth2AccessToken对象
                    authorization = authorization.replace("Bearer ", "");
                    OAuth2AccessToken accessToken = tokenServices.readAccessToken(authorization);
                    //判断token是否过期
                    if (accessToken.isExpired()){
                        setErrorResponse(context,401,"access token is expired!");
                        return null;
                    }

                    //获取scope和role
                    AuthorizationRouter authorizationRouter = routerMap.get(serviceName).get(router);
                    Set<String> scopes = accessToken.getScope();
                    ArrayList<String> roles = (ArrayList<String>) accessToken.getAdditionalInformation().get(AUTHORITIES_IN_JWT);
                    //判断scope和role
                    Boolean containsScope=false;
                    Boolean containsRole=false;
                    for(String scope:scopes)
                    {
                        if (authorizationRouter.getScope().contains(scope)) {
                            containsScope = true;
                            break;
                        }
                    }
                    for(String role:roles)
                    {
                        if (authorizationRouter.getRole().contains(role)) {
                            containsRole = true;
                            break;
                        }
                    }
                    if (!containsScope){
                        setErrorResponse(context,401,"scope is error!");
                        return null;
                    }
                    if (!containsRole){
                        setErrorResponse(context,401,"role is error!");
                        return null;
                    }

                    //获取username，并写出到请求header中
                    String username = (String)accessToken.getAdditionalInformation().get(USER_NAME_IN_JWT);
                    RequestContext.getCurrentContext().addZuulRequestHeader(USER_NAME_IN_HEADER, username);

                    setSuccessResponse(context);
                    return null;
                } catch (Exception e) {
                    setErrorResponse(context,401,"access token is error!");
                    return null;
                }
            }
        }
        //为了适配，设置匿名头部
        RequestContext.getCurrentContext().addZuulRequestHeader(USER_NAME_IN_HEADER, "");
        setErrorResponse(context,401,"access token is empty!");
        return null;
    }

    private boolean isJwtBearerToken(String token) {
        return StringUtils.countMatches(token, ".") == 2 && (token.startsWith("Bearer") || token.startsWith("bearer"));
    }

    private void setSuccessResponse(RequestContext context){
        // 对该请求进行路由，默认true
        context.setSendZuulResponse(true);
        context.set("isSuccess", true);
    }

    private void setErrorResponse(RequestContext context, int code, String error){
        ResultUtils result = ResultUtils.fail(code, error);
        // 过滤该请求，不对其进行路由
        context.setSendZuulResponse(false);
        // 返回错误码
        context.setResponseStatusCode(code);
        context.getResponse().setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        context.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 返回错误内容
        context.setResponseBody(JSON.toJSONString(result));
        context.set("isSuccess", false);
    }
}
