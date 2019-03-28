package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Value("${helloValue}")
    private String hello; //省略getter setter方法

    @RequestMapping("/hello")
    public String index(){
        return hello;
    }

}
