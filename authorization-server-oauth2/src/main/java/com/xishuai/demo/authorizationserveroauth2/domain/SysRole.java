package com.xishuai.demo.authorizationserveroauth2.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysRole {
    private Integer id;
    private String name;
    private List<SysUser> users;

    public String getName() {
        return name;
    }

    public List<SysUser> getUsers() {
        return users;
    }
}
