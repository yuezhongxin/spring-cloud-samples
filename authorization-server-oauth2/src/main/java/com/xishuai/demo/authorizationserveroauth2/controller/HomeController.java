package com.xishuai.demo.authorizationserveroauth2.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() throws Exception{
        throw new Exception("test error");
        //return "hello world!";
    }

}
