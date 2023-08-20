package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping("/t1")
    public String t1(){
        System.out.println("t1");
        return "hello t1";
    }

    @ResponseBody
    @PostMapping("/toMain")
    public String toMain(){
        System.out.println("to Main");
        return "Main";
    }

    @ResponseBody
    @PostMapping("/toError")
    public String toError(){
        System.out.println("to Error");
        return "Error";
    }

    @ResponseBody
    @RequestMapping("/afterLoginNeedAuthority")
    public String afterLoginNeedAuthority(){
        System.out.println("need admin authority");
        return "hello admin";
    }

    @ResponseBody
    @RequestMapping("/afterLoginNeedRole")
    public String afterLoginNeedRole(){
        System.out.println("need abc Role");
        return "hello abc";
    }




}
