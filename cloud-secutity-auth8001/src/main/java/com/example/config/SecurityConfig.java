package com.example.config;

import com.example.handler.MyAuthenticationFailHandler;
import com.example.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//security配置类
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        //官方体检的加密逻辑
        return new BCryptPasswordEncoder();
    }

    //登录设置
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //表单提交
        http.formLogin()
                // 当发现访问路径的是/login时【默认是/auth好像】，会去调用自定义的认证逻辑，去执行UserDetailServiceImpl方法
                // 因此必须与login.html页面中的表单提交的位置一致，这样一点击登录就调用了security里边的逻辑了
                .loginProcessingUrl("/login")
                //使用自定义的登录页面[static下边]，而不是spring-security自带的页面
                .loginPage("/login.html")
                    //只能跳转到站内资源【会使用dispatcher().forward()方法，请求转发】
//                        //登陆成功后跳转的页面，必须是Post请求[因此要自定义post处理，直接写html页面是get请求]
//                        .successForwardUrl("/toMain")
//                        //登录失败后跳转的页面，也是Post请求
//                        .failureForwardUrl("/toError");
                    .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))
                    .failureHandler(new MyAuthenticationFailHandler("http://www.bilibili.com"));


        //和mvc拦截器类似，设置放行和拦截的路径
        http.authorizeRequests()
                //前边是路径匹配，后边是权限控制
                //放行登录页面，到了登录页面也要认证的话，就会循环访问登陆页面
                .antMatchers("/login.html").permitAll()
                //配置访问某个路径所需要的权限[在/afterLogin方法上使用注解也可以]
//                .antMatchers("/afterLoginNeedAuthority").hasAnyAuthority("admin")
                //配置某个访问路径需要某角色才能访问【这个地方不加Role_前缀】
                .antMatchers("/afterLoginNeedRole").hasRole("abc,XXX")
                //配置某个访问路径要具有特定ip才能访问【这个时候就不能使用localhost进行访问了】
                .antMatchers("/afterLoginNeedIpAddr").hasIpAddress("127.0.0.1")
                //表示所有认证都要登录后才能访问
                .anyRequest().authenticated();
        //关闭
        http.csrf().disable();
    }



}
