package com.example.java.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListRedisUtil extends RedisUtil{
    @Autowired
    private ListOperations<String,Object> list;

    public Long leftPush(String key,Object... obj){
        try {
            if(obj.length==1)
                return list.leftPush(key,obj);
            else
                return list.leftPushAll(key, obj);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long rightPush(String key,Object... obj){
        try {
            if(obj.length==1)
                return list.rightPush(key,obj);
            else
                return list.rightPushAll(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated(since = "windows并不支持")
    public List<Object> leftPop(String key,int count){
        try {
            return list.leftPop(key,count);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object leftPop(String key){
        try {
            return list.leftPop(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated(since = "windows并不支持")
    public List<Object> rightPop(String key,int count){
        try {
            return list.rightPop(key,count);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object rightPop(String key){
        try {
            return list.rightPop(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> range(String key,int start,int end){
        try {
            return list.range(key,start,end);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public List<Object> range(String key,int start){
        return range(key,start,-1);
    }

    public List<Object> range(String key){
        return range(key,0,-1);
    }

    public Long length(String key){
        try {
            return list.size(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object index(String key,int index){
        try {
            return list.index(key,index);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long indexOf(String key,Object obj){
        try {
            return list.indexOf(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean reset(String key,int index,Object obj){
        try {
            list.set(key,index,obj);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Long remove(String key,int count,Object obj){
        try {
            return list.remove(key,count,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long remove(String key,Object obj){
        try {
            return list.remove(key,1,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean trim(String key,int start,int end){
        try {
            list.trim(key,start,end);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
