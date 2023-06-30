package com.example.java.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Component;

@Component
public class RegisterDto {
    public static final String NAME_REPEAT="用户名已被使用";
    public static final String ACCOUNT_REPEAT="账号已被使用";
    public static final String TWICE_PASSWORD_DISABLE="两次密码不相同";
    public static final String CHECK_NO_PASS="用户表单填写错误";

    @Size(min = 6,max = 16,message = "账号在6-16个字符之间")
    public String account;

    @Size(min = 6,max = 16,message = "密码在6-16个字符之间")
    public String password;

    @Size(max = 10,message = "名字最大只有十个字符")
    public String username;

    @Size(min = 6,max = 16,message = "密码在6-16个字符之间")
    public String confirmedPassword;
}
