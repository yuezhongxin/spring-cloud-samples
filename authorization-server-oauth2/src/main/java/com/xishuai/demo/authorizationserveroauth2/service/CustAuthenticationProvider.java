package com.xishuai.demo.authorizationserveroauth2.service;

import com.xishuai.demo.authorizationserveroauth2.repository.UserDetailsImpl;
import com.xishuai.demo.authorizationserveroauth2.util.HttpHeaderUtils;
import com.xishuai.demo.authorizationserveroauth2.util.SmsKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

//自定义AuthenticationProvider，用于手机短信验证码登录
@Component
public class CustAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    DBUserDetailsService userDetailsService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        String deviceName = request.getHeader(HttpHeaderUtils.DEVICE);
        if (StringUtils.isEmpty(deviceName)) {
            deviceName = HttpHeaderUtils.DEFAULT_DEVICE;
        }

        //优先匹配密码
        if (passwordEncoder().matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        } else {
            //从redis中读取验证码
            String key = SmsKeyUtils.getSmsCodeKey(deviceName, username);
            String code = stringRedisTemplate.opsForValue().get(key);
            if (!password.equals(code)) {
                throw new BadCredentialsException("验证码错误");
            }
            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
        //return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
