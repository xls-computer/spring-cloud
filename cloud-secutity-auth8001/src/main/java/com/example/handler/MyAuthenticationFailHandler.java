package com.example.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义登录成功后跳转的逻辑（可以实现站外的跳转，用于前后端分离）
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {
    private String url;

    public MyAuthenticationFailHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println();
        //使用重定向就能实现站外的跳转了
        httpServletResponse.sendRedirect(url);
    }
}
