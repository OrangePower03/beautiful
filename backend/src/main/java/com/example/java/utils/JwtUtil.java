package com.example.java.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtUtil {

    private static final String secret="!DSG@$%^%DSFQ#$^!#$Y";

    private static final long expireTime=60*60*1000; //十分钟过期

    private static JWTCreator.Builder builder;

    private static DecodedJWT decoded;

    private static JwtUtil jwt=new JwtUtil();

    private JwtUtil(){}

    public static JwtUtil build(Map<String,Object> header, Map<String,String> claim){
        builder = JWT.create();
        builder.withHeader(header);
        claim.forEach(builder::withClaim);
        long nowTime=System.currentTimeMillis();
        builder.withExpiresAt(new Date(nowTime+expireTime));
        return jwt;
    }

    public static JwtUtil build(Map<String,String> claim){
        builder = JWT.create();
        claim.forEach(builder::withClaim);
        long nowTime=System.currentTimeMillis();
        builder.withExpiresAt(new Date(nowTime+expireTime));
        return jwt;
    }

    public static JwtUtil build() {
        return build(new HashMap<>(0));
    }

    public JwtUtil setExpire(long timeout, TimeUnit timeUnit) throws RuntimeException{
        long time=switch (timeUnit){
            case SECONDS -> timeout*1000;
            case MINUTES -> timeout*1000*60;
            case HOURS -> timeout*1000*60*60;
            case DAYS -> timeout*1000*60*60*24;
            default -> -1;
        };
        if(time < 0)
            throw new RuntimeException("输入的 timeUnit 不对，只能输入秒分时天");
        builder.withExpiresAt(new Date(System.currentTimeMillis()+time));
        return this;
    }

    public String getToken() {
        return builder.sign(Algorithm.HMAC256(secret));
    }

    public static JwtUtil verify(String token){
        decoded=JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        return jwt;
    }

    public String getClaim(String name){
        return decoded.getClaim(name).asString();
    }

    public Map<String,String> getClaims(){
        Map<String,String> result=new HashMap<>();
        decoded.getClaims().forEach((key,value)->{
            result.put(key,value.asString());
        });
        return result;
    }

    public Date getExpiresAt() {
        return decoded.getExpiresAt();
    }
}
