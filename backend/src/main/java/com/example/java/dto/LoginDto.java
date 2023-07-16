package com.example.java.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginDto {
    public static final String ACCOUNT_EMPTY="账号不存在";
    public static final String PASSWORD_ERROR ="密码错误";

    public Integer uid;
    public String name;
    public String account;
    public String password;
    private Integer tag=0;

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", tag=" + tag +
                '}';
    }
}
