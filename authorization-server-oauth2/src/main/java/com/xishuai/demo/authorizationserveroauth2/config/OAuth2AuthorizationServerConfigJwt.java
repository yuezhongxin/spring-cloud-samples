package com.xishuai.demo.authorizationserveroauth2.config;

import com.xishuai.demo.authorizationserveroauth2.handler.CustomOauthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigJwt extends AuthorizationServerConfigurerAdapter {

    //设置这个client可以访问哪一些微服务实例，如果没设置，就是对所有的resource都有访问权限。
    private static final String[] RESOURCE_IDS = new String[]{ "resource-server", "zuul-api-gateway" };

    /**
     * 注入authenticationManager
     * 来支持 password grant type
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()") //url: /oauth/token_key,exposes public key for token verification if using JWT tokens
            .checkTokenAccess("permitAll()") //url: /oauth/check_token allow check token
            .allowFormAuthenticationForClients(); //禁用basic auth，参考：https://stackoverflow.com/questions/38166613/spring-oauth2-disable-http-basic-auth-for-tokenendpoint
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("sampleClientId")
            .authorizedGrantTypes("implicit")
            .scopes("read", "write", "foo", "bar")
            .autoApprove(false)
            .accessTokenValiditySeconds(3600)

            .and()
            .withClient("fooClientIdPassword")
            .secret("secret")
            .authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
            .scopes("foo", "read", "write")
            .resourceIds(RESOURCE_IDS)
            .accessTokenValiditySeconds(3600)
            // 1 hour
            .refreshTokenValiditySeconds(2592000)
            // 30 days
            //备注：客户端模式不具有刷新token的功能，返回的数据中不包含refresh_token，参考：https://stackoverflow.com/questions/46232331/refresh-token-is-not-returned-in-oauth-token-response-of-spring?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

            .and()
            .withClient("barClientIdPassword")
            .secret("secret")
            .authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
            .scopes("bar", "read", "write")
            .accessTokenValiditySeconds(3600)
            // 1 hour
            .refreshTokenValiditySeconds(2592000)
            // 30 days

            .and()
            .withClient("reportH5")
            .secret("asg%3hd&126Rg2")
            .authorizedGrantTypes("password", "refresh_token")
            .scopes("read", "write")
            .resourceIds(RESOURCE_IDS)
            .accessTokenValiditySeconds(604800)
            // 7 days
            .refreshTokenValiditySeconds(2592000)
            // 30 days
        ;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        //token存储配置
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //token生成配置
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        endpoints.tokenStore(tokenStore())
            .tokenEnhancer(tokenEnhancerChain)
            .authenticationManager(authenticationManager);

        //自定义异常返回格式
        //参考：https://medium.com/@beladiyahardik7/spring-security-custom-oauth2exception-in-spring-b35a62af4d34
        endpoints.exceptionTranslator(exception -> {
            if (exception instanceof OAuth2Exception) {
                OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                return ResponseEntity
                        .status(oAuth2Exception.getHttpErrorCode())
                        .body(new CustomOauthException(oAuth2Exception.getMessage()));
            } else {
                throw exception;
            }
        });
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //token rsa加密；配置
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //converter.setSigningKey("123");
        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt-key.jks"), "123qweasd".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt-key"));
        return converter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

}
