package org.example.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

//资源服务器下的资源
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前用户
     * 对jwt继续接续
     * @param authentication
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(header.indexOf("bearer") + 7);

        Claims body = Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return body;
//        return authentication.getPrincipal();
    }



}
