package com.example.java.controller;

import com.example.java.dto.LoginDto;
import com.example.java.dto.RegisterDto;
import com.example.java.mapper.UserMapper;
import com.example.java.myExcetion.LoginException;
import com.example.java.myExcetion.RegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@CrossOrigin("http://localhost:5173")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    
    @PostMapping("/register")
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

     @PostMapping("/login")
     public ResponseEntity<LoginDto> login(@RequestBody LoginDto login) {
        if(userMapper.findUidByAccount(login.account).isEmpty()){
            throw new LoginException(LoginDto.ACCOUNT_EMPTY);
        }
        if(userMapper.findUidByPassword(login.password).isEmpty()){
            throw new LoginException(LoginDto.PASSWORD_ERROR);
        }

        login.username=userMapper.findUserNameByAccount(login.account).get(0);
        login.password="";
        login.setTag(userMapper.findTagByAccount(login.account));
        login.setRandomString(UUID.randomUUID().toString());
        System.out.println(login);
        return new ResponseEntity<>(login,HttpStatus.OK);
     }
}
