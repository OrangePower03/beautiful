package com.example.java.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void rename(String key,String newName){
        try {
            redisTemplate.rename(key,newName);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public DataType type(String key){
        try {
            return redisTemplate.type(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long expire(String key, long timeout, TimeUnit timeUnit){
        try {
            if(Boolean.TRUE.equals(redisTemplate.hasKey(key))){
                if(timeUnit.equals(TimeUnit.SECONDS))
                    redisTemplate.expire(key, Duration.ofSeconds(timeout));
                else if(timeUnit.equals(TimeUnit.MINUTES))
                    redisTemplate.expire(key,Duration.ofMinutes(timeout));
                else if(timeUnit.equals(TimeUnit.HOURS))
                    redisTemplate.expire(key,Duration.ofHours(timeout));
                else if(timeUnit.equals(TimeUnit.DAYS))
                    redisTemplate.expire(key,Duration.ofDays(timeout));
                else
                    throw new RuntimeException("不合法输入");
            }
            else {
                throw new RuntimeException("不存在的键名，可能已经过期了");
            }
            return timeout;
        }
        catch(Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public Boolean keyExist(String key){
        try {
            return redisTemplate.hasKey(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long getTimeOfLive(String key) {
        try {
            return redisTemplate.getExpire(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean delete(String key){
        try {
            return redisTemplate.delete(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
