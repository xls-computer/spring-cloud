package com.example.handler.impl;

import com.example.handler.MyAccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
public class MyAccessServiceImpl implements MyAccessService {

    //配置只有当用户具有当前页面的url权限，才能放行
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            System.out.println(userDetails);
            //获取权限
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            //有就放行
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }

        return false;
    }
}
