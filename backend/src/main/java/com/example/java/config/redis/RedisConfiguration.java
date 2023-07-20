package com.example.java.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(
                                 LettuceConnectionFactory factory){
        // 以下是默认的标准redis模板类，可以去看redis的自动配置
        RedisTemplate<String,Object> redis=new RedisTemplate<>();
        redis.setConnectionFactory(factory);

        // 配置 序列化器
        /*
        private RedisSerializer keySerializer = null;
        private RedisSerializer valueSerializer = null;
        private RedisSerializer hashKeySerializer = null;
        private RedisSerializer hashValueSerializer = null;

        RedisSerializer 有好几个实现类，web开发使用json的好
        */
        redis.setKeySerializer(new StringRedisSerializer());  //key一般都是String
        redis.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        redis.setHashKeySerializer(new StringRedisSerializer());
        redis.setHashValueSerializer(new FastJsonRedisSerializer<>(Object.class));

        // 用来检查你有没有设置上面那个工厂的
        redis.afterPropertiesSet();
        return redis;
    }
}
