package com.xishuai.demo.authorizationserveroauth2.config;

import com.xishuai.demo.authorizationserveroauth2.service.CustAuthenticationProvider;
import com.xishuai.demo.authorizationserveroauth2.service.DBUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DBUserDetailsService userDetailsService;

    @Autowired
    private CustAuthenticationProvider custAuthenticationProvider;

    /*
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
            /*auth.inMemoryAuthentication()
              .withUser("john").password("123").roles("USER").and()
              .withUser("tom").password("111").roles("ADMIN").and()
              .withUser("user1").password("pass").roles("USER").and()
              .withUser("admin").password("nimda").roles("ADMIN");*/

        //配置用户来源于数据库
        auth.userDetailsService(userDetailsService);
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义AuthenticationProvider（授权验证）
        auth.authenticationProvider(custAuthenticationProvider);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/oauth/token/revokeById/**").permitAll()
                .antMatchers("/oauth/revoke").permitAll()
                .antMatchers("/tokens/**").permitAll()
                .antMatchers("/user/contains/**").permitAll()
                .antMatchers("/home").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().csrf().disable();
        // @formatter:on
    }
}
