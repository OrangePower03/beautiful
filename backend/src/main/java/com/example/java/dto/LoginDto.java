package com.example.java.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LoginDto implements UserDetails {
    public static final String ACCOUNT_EMPTY="账号不存在";
    public static final String PASSWORD_ERROR ="密码错误";
    public static final String TOKEN_UNAUTHORIZED="token非法";
    public static final String UNVERIFIED ="用户未登录";

    public Integer uid;
    public String name;
    public String account;

    @JSONField(serialize = false)
    private String password;

    private Integer tag=0;
    private List<String> permissions=new ArrayList<>(0);

    @JSONField(serialize = false) // 拒绝redis的序列化
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public String toString() {
        return "LoginDto{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", tag=" + tag +
                ", permissions='" + permissions + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    @Override
    public String getUsername() {
        return name;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(List<String> roles){
        permissions= roles;
        authorities = permissions.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities == null){
            authorities = permissions.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
        }
        return authorities;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
