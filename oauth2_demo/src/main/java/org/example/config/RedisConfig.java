package org.example.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

//使用redis来存储token的方式会使用到如下的redis和pool依赖，使用jwt不会使用到
//@Configuration
//public class RedisConfig {
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    //原来的Token是存储在内存中的，这里使用redis进行存储
//    @Bean
//    public TokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//
//}
