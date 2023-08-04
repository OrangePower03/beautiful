package com.example.java.security;

import com.example.java.dto.LoginDto;
import com.example.java.mapper.RoleMapper;
import com.example.java.mapper.UserMapper;
import com.example.java.myExcetion.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 查询该用户的对应的信息，
        System.out.println("账号："+account);
        LoginDto login= userMapper.findUserByAccount(account);
        if(Objects.isNull(login)){
            throw new LoginException(LoginDto.ACCOUNT_EMPTY);
        }

        // 查询权限
        List<String> roles = roleMapper.findRoleByAccount(account);
        System.out.println(roles);
        login.setRole(roles);

        // 返回 UserDetails 对象
        return login;
    }
}
