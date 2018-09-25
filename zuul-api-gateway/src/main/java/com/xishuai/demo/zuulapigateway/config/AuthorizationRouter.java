package com.xishuai.demo.zuulapigateway.config;

/**
 * 路由授权实体
 */
public class AuthorizationRouter {
    private String scope;
    private String role;


    public String getScope() {
        return scope;
    }

    public String getRole() {
        return role;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
