package com.example.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义登录成功后跳转的逻辑（可以实现站外的跳转，用于前后端分离）
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    //还携带了authentication对象
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //远程的ip地址
        String remoteHost = httpServletRequest.getRemoteHost();
        System.out.println("remoteHost: "+remoteHost);

        System.out.println("RequestURI: "+httpServletRequest.getRequestURI());

        //获取到User的信息
        User user = (User) authentication.getPrincipal();
        System.out.println("User info : ");
        System.out.println("             name: "+user.getUsername());
        System.out.println("             passw: "+user.getPassword());
        System.out.println("             authority: "+user.getAuthorities());
        //使用重定向就能实现站外的跳转了
        httpServletResponse.sendRedirect(url);
    }
}
