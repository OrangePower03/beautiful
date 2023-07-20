package com.example.java.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HashRedisUtil extends RedisUtil{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private HashOperations<String,String,Object> hash;

    public boolean set(String key,String hashKey,Object obj){
        try {
            hash.put(key,hashKey,obj);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int setMore(String key, Map<String,String> map){
        try {
            hash.putAll(key,map);
            return map.size();
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Object get(String key,String hashKey){
        try {
            return hash.get(key,hashKey);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public List<Object> getMore(String key,String... hashKeys){
        try {
            return hash.multiGet(key, Arrays.stream(hashKeys).toList());
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public Map<String,Object> getFieldsAndValues(String key){
        try {
            return hash.entries(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new HashMap<>(0);
        }
    }

    public List<Object> getAllValues(String key){
        try {
            return hash.values(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public boolean setNoExist(String key,String hashKey,Object obj){
        try {
            hash.putIfAbsent(key,hashKey,obj);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String key,String... hashKeys){
        try {
            hash.delete(key, (Object)hashKeys);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean hashKeyExist(String key,String hashKey){
        try {
            return hash.hasKey(key,hashKey);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Double incr(String key,String hashKey,Double number){
        try {
            return hash.increment(key,hashKey,number);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
