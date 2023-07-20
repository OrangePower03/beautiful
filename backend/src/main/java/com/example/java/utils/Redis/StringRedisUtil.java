package com.example.java.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class StringRedisUtil extends RedisUtil{
    @Autowired
    private ValueOperations<String,Object> value;

    public boolean set(String key,Object obj){
        try {
            value.set(key,obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Object get(String key){
        return value.get(key);

    }

    public boolean append(String key,String str){
        try {
            value.append(key,str);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Double incrBy(String key,Double number){
        try {
            return value.increment(key,number);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Long decrBy(String key,Long seconds){
        try {
            return value.decrement(key,seconds);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Boolean setNoExist(String key,Object obj){
        try {
            return value.setIfAbsent(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long setWithExpireSeconds(String key, Object obj, long timeout) {
        try {
            value.set(key,obj,timeout, TimeUnit.SECONDS);
            return getTimeOfLive(key);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long setWithExpireMinutes(String key, Object obj, long timeout) {
        try {
            value.set(key,obj,timeout, TimeUnit.MINUTES);
            return getTimeOfLive(key);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long setWithExpireHours(String key, Object obj, long timeout) {
        try {
            value.set(key,obj,timeout, TimeUnit.HOURS);
            return getTimeOfLive(key);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
