package com.example.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//必须有admin角色才能访问
@RestController
public class TestAnnotationController {

    @Secured("ROLE_abc")
    @RequestMapping("/test1")
    public String test1(){
        System.out.println("test1");
        return "test1";
    }

    //可以加Role_也可以不加
    @PreAuthorize("hasRole('abc')")
//    @PreAuthorize("hasRole('ROLE_abc')")
    @RequestMapping("/test2")
    public String test2(){
        System.out.println("test2");
        return "test2";
    }




}
