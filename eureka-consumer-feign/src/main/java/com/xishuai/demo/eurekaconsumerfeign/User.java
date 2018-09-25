package com.xishuai.demo.eurekaconsumerfeign;

public class User {
    private long id;
    private String userName;
    private String email;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
