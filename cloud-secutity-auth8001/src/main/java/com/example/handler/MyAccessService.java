package com.example.handler;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

//自定义访问控制，实现用户根据自己的逻辑进行访问控制
public interface MyAccessService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
