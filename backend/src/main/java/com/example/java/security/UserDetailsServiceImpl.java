package com.example.java.security;

import com.example.java.dto.LoginDto;
import com.example.java.mapper.UserMapper;
import com.example.java.myExcetion.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private AuthenticationMapper authenticationMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 查询该用户的对应的信息，
        System.out.println("账号："+account);
        LoginDto login= userMapper.findUserByAccount(account);
        if(Objects.isNull(login)){
            throw new LoginException(LoginDto.ACCOUNT_EMPTY);
        }

        // todo 查询权限
//        login.setRole(authenticationMapper,account);
        // 返回 UserDetails 对象
        return login;
    }
}
