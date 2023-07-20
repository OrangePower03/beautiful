package com.example.java.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SetRedisUtil extends RedisUtil{
    @Autowired
    private SetOperations<String,Object> set;

    public Long add(String key,Object... obj){
        try {
            return set.add(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long add(String key,Object obj){
        try {
            return set.add(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long add(String key, Set<Object> originSet){
        try {
            Object[] array = originSet.toArray();
            for(int i=0;i<originSet.size();i++){
                set.add(key,array[i]);
            }
            return (long)array.length;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<Object> toSet(String key){
        try {
            return set.members(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new HashSet<>(0);
        }
    }

    public Object randomMember(String key){
        try {
            return set.randomMember(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> randomMembers(String  key, int count){
        try {
            return set.randomMembers(key,count);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public Object randomPop(String key){
        try {
            return set.pop(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long remove(String key,Object... obj){
        try {
            return set.remove(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long length(String key){
        try {
            return set.size(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean setKeyExist(String key,Object obj){
        try {
            return set.isMember(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<Object> intersection(List<String> keys){
        try {
            return set.intersect(keys);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new HashSet<>(0);
        }
    }

    public Set<Object> union(List<String> keys){
        try {
            return set.union(keys);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<Object> difference(String leftKey,String rightKey){
        try {
            return set.difference(leftKey,rightKey);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new HashSet<>(0);
        }
    }

    public List<String> keysToList(String... keys){
        return new ArrayList<>(Arrays.stream(keys).toList());
    }
}
