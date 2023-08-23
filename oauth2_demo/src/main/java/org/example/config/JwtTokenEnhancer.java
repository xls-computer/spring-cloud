package org.example.config;

import org.apache.commons.codec.BinaryDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/**
 * Jwt的内容增强器（就是payload字段里边可以自定义的添加一些信息，即声明）
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    //在jwt的payload字段增加声明
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        HashMap<String, Object> info = new HashMap<>();
        info.put("enhance1", "enhance1 info");
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
