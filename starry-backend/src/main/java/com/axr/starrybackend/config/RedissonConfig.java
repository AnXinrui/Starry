package com.axr.starrybackend.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedissonConfig redis配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis") // 读取配置文件
public class RedissonConfig {
    private String host;
    private String port;
    private String password;

    @Bean // 可以通过 @Resource 注解注入
    public RedissonClient redissonClient() {
        // 1. Create config object
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", host, port);
        config.useSingleServer()
                .setAddress(redisAddress)
                .setPassword(password)
                .setDatabase(3);
        // 2. 创建实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}