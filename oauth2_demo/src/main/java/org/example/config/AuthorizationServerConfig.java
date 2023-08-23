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
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 使用密码模式需要
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置jwt内容增强器
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //使用redis来存储token的方式会使用到如下的redis和pool依赖，使用jwt不会使用到
                //使用redis存储token
//                .tokenStore(redisTokenStore);
                //使用jwt存储token
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                //将增强器配置到里边
                .tokenEnhancer(tokenEnhancerChain);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //放在内存【这里只是演示】
        clients.inMemory()
                //配置client-id
                .withClient("admin")
                //配置client-secret
                .secret(passwordEncoder.encode("123456"))
                //配置token的有效期
                .accessTokenValiditySeconds(3600)
                //配置refresh_token的有效期
                .refreshTokenValiditySeconds(3600*24)
                //配置redirect_uri，用于授权成功后跳转
//                .redirectUris("http://www.baidu.com")
                //这里是测试单点登录,登录成功后让它跳回客户端
                .redirectUris("http://localhost:8081/login")
                //自动授权配置(不用在需要授权的时候,在认证服务器页面再点击允许授权)
                .autoApprove(true)
                //配置申请权限范围
                .scopes("all")
                //配置grant_type，表示授权类型
//                .authorizedGrantTypes("authorization_code");
                //修改为密码模式        ,refresh_token标识支持令牌刷新【可以加多个授权类型】
                //要支持单点登录,必须要有authorization_code模式支持
                .authorizedGrantTypes("password", "refresh_token", "authorization_code");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //使用单点登录需要配置的功能
        security.tokenKeyAccess("isAuthenticated()");
    }
}
