package com.xishuai.demo.authorizationserveroauth2.config;

import com.xishuai.demo.authorizationserveroauth2.repository.UserDetailsImpl;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义claims，存储于jwt token中，网关可以解析获取
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        if (authentication.getUserAuthentication() != null){
            UserDetailsImpl user = (UserDetailsImpl) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put("phone", user.getPhone());
            additionalInfo.put("authorities", user.getArrayAuthorities());
            //additionalInfo.put("username", user.getUsername());
            //additionalInfo.put("authorities", user.getAuthorities());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
