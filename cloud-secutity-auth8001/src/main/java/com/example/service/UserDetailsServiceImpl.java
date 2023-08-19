package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//自定义登录逻辑
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    //从db中查询，返回一个UserDetails对象
    //与前端进行对比
    //db里边存储的加密后的密码，前端传入的密码在进行对比的时候也会进行加密
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username " + username + " is trying to login");
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("user not exist");
        }

        return new User("admin", passwordEncoder.encode("123"),
                //权限不能为null
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal"));
    }
}
