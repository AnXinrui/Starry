package com.axr.starrybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration // 标注这是一个配置类
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setStringSerializer(RedisSerializer.string());
        return redisTemplate;
    }
}

/*
redis 本身有默认序列化器，
如果不设置序列化器，redisTemplate 默认使用的是 JdkSerializationRedisSerializer
我们指定 string 序列化器，这样存入的数据就是字符串，方便查看
*/