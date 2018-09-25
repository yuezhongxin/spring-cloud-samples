package com.xishuai.demo.authorizationserveroauth2.controller;

import com.xishuai.demo.authorizationserveroauth2.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private SysUserRepository sysUserMapper;

    @GetMapping("/user/contains/{phone}")
    @ResponseBody
    public Boolean contains(@PathVariable String phone) {

        return sysUserMapper.containsByPhone(phone);
    }
}
