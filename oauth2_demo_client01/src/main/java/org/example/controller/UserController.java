package org.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//资源服务器下的资源
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前用户
     * @param authentication
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication;
    }



}
