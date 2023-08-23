package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

//认证服务器配置
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;

    //使用密码模式需要
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;

    //使用redis来存储token的方式会使用到如下的redis和pool依赖，使用jwt不会使用到
    //使用redis存储token
//    @Autowired
//    @Qualifier("redisTokenStore")
//    TokenStore redisTokenStore;

    //使用jwt存储token
    @Autowired
    @Qualifier("jwtTokenStore")
    TokenStore tokenStore;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 使用密码模式需要
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //使用redis来存储token的方式会使用到如下的redis和pool依赖，使用jwt不会使用到
                //使用redis存储token
//                .tokenStore(redisTokenStore);
                //使用jwt存储token
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //放在内存【这里只是演示】
        clients.inMemory()
                //配置client-id
                .withClient("admin")
                //配置client-secret
                .secret(passwordEncoder.encode("123456"))
                //配置token有效期
                .accessTokenValiditySeconds(3600)
                //配置redirect_uri，用于授权成功后跳转
                .redirectUris("http://www.baidu.com")
                //配置申请权限范伟
                .scopes("all")
                //配置grant_type，表示授权类型
//                .authorizedGrantTypes("authorization_code");
                //修改为密码模式
                .authorizedGrantTypes("password");


    }
}
