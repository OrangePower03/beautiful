package com.example.java.utils.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.*;

@Configuration
public class RedisTemplateUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Bean
    public ValueOperations<String, Object> stringRedis(){
        return redisTemplate.opsForValue();
    }

    @Bean
    public HashOperations<String,String, Object> hashRedis(){
        return redisTemplate.opsForHash();
    }

    @Bean
    public ListOperations<String,Object> listRedis(){
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String,Object> setRedis(){
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String,Object> zSetRedis(){
        return redisTemplate.opsForZSet();
    }
}
