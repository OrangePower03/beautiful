package com.example.java.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ZSetRedisUtil extends RedisUtil{
    @Autowired
    private ZSetOperations<String,Object> zSet;

    public Boolean add(String key,Object value,double score){
        try {
            return zSet.add(key,value,score);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean addNoExist(String key,Object value,double score){
        try {
            return zSet.addIfAbsent(key,value,score);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long remove(String key,Object... obj){
        try {
            return zSet.remove(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double incrScore(String key,Object obj,double increment){
        try {
            return zSet.incrementScore(key,obj,increment);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getRank(String key,Object obj){
        try {
            return zSet.rank(key,obj);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<Object> range(String key,int start,int end){
        try {
            return zSet.range(key,start,end);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new HashSet<>(0);
        }
    }

    public Set<Object> range(String key,int start){
        return range(key,start,-1);
    }

    public Set<Object> range(String key){
        return range(key,0,-1);
    }

    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(
                                   String key,int start,int end){
        try {
            return zSet.rangeWithScores(key,start,end);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new HashSet<>(0);
        }
    }

    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key,int start){
        return rangeWithScores(key,start,-1);
    }

    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key){
        return rangeWithScores(key,0,-1);
    }

    public Long count(String key,double min,double max){
        try {
            return zSet.count(key,min,max);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long count(String key,double min){
        return count(key,min,Double.MAX_VALUE);
    }

    public Long count(String key){
        return count(key,Double.MIN_VALUE,Double.MAX_VALUE);
    }

    public ZSetOperations.TypedTuple<Object> popMin(String key){
        try {
            return zSet.popMin(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ZSetOperations.TypedTuple<Object> popMax(String key){
        try {
            return zSet.popMax(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long length(String key){
        try {
            return zSet.size(key);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long remove(String key,int start,int end){
        try {
            return zSet.removeRange(key,start,end);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
