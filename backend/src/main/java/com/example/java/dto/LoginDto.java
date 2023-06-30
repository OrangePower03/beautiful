package com.example.java.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginDto {
    public static final String ACCOUNT_EMPTY="账号不存在";
    public static final String PASSWORD_ERROR ="密码错误";

    public String username;
    public String account;
    public String password;
    private Integer tag=0;
    private String randomString;

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public String getRandomString(){
        return randomString;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", tag=" + tag +
                ", randomString='" + randomString + '\'' +
                '}';
    }
}
