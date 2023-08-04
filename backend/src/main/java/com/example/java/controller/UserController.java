package com.example.java.controller;

import com.example.java.dto.LoginDto;
import com.example.java.dto.RegisterDto;
import com.example.java.mapper.UserMapper;
import com.example.java.myExcetion.RegisterException;
import com.example.java.utils.JwtUtil;
import com.example.java.utils.Redis.StringRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;


@CrossOrigin("http://localhost:5173")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private StringRedisUtil redisUtil;
    
    @PostMapping("/user/register")
    public ResponseEntity<Map<String,String>> register(@Validated @RequestBody
                                 RegisterDto register, BindingResult check){
        if(!register.password.equals(register.confirmedPassword)){
            throw new RegisterException(RegisterDto.TWICE_PASSWORD_DISABLE);
        }
        if(check.hasErrors()){
            throw new RegisterException(RegisterDto.CHECK_NO_PASS,check);
        }
        if(!userMapper.findUidByName(register.username).isEmpty()){
            throw new RegisterException(RegisterDto.NAME_REPEAT);
        }
        if(!userMapper.findByAccount(register.account).isEmpty()){
            throw new RegisterException(RegisterDto.ACCOUNT_REPEAT);
        }
        // 密码密文存储
        register.password=passwordEncoder.encode(register.password);
        int success=userMapper.addUser(register);
        if(success>0){
            Map<String,String> map=new HashMap<>();
            map.put("name",register.username);
            map.put("account",register.account);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else{
            throw new RegisterException("未知异常");
        }
    }

     @PostMapping("/user/login")
     public ResponseEntity<Map<String,String>> login(@RequestBody LoginDto login) {
         Map<String,String> map=new HashMap<>();
//         LoginDto user = userMapper.findUserByAccount(login.account);
         UsernamePasswordAuthenticationToken authenticationToken=new
                UsernamePasswordAuthenticationToken(login.account,login.getPassword());
         Authentication authenticate = manager.authenticate(authenticationToken);
         LoginDto user=(LoginDto)authenticate.getPrincipal();

//         if(Objects.isNull(user)){
//             throw new LoginException(LoginDto.ACCOUNT_EMPTY);
//         }
//         else if(!login.getPassword().equals(user.getPassword())){
//             throw new LoginException(LoginDto.PASSWORD_ERROR);
//         }
//         else {
         String userId=user.uid.toString();

         map.put("id",userId);
         map.put("account",user.account);
         map.put("username",user.name);
         map.put("tag",user.getTag().toString());

         String token=JwtUtil.build(map).getToken();

         map.put("token",token);

         Date expireTime=JwtUtil.verify(token).getExpiresAt();
         long minute=(expireTime.getTime()-System.currentTimeMillis())/60000;
         map.put("exp", minute +"分钟");

         redisUtil.set("user:"+userId,user);

         redisUtil.expire("user:"+userId,minute, TimeUnit.MINUTES);
         return new ResponseEntity<>(map,HttpStatus.OK);
//         }
     }

    @PostMapping("/user/logout")
    public void logout(){
        System.out.println("用户正在退出登录");
        UsernamePasswordAuthenticationToken authentication =
        (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        LoginDto user = (LoginDto)authentication.getPrincipal();
        try {
            System.out.println("用户id："+user.uid);
            redisUtil.delete("user:"+user.uid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("验证失败");
        }
     }
}
