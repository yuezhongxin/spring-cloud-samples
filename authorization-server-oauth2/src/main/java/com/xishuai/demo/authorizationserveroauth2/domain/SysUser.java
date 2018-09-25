package com.xishuai.demo.authorizationserveroauth2.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private List<SysRole> roles;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public List<SysRole> getRoles() {
        return roles;
    }
}
