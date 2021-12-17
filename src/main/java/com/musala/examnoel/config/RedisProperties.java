package com.musala.examnoel.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;


@Configuration
@Data
public class RedisProperties {

    private RedisServer redisServer;

    public RedisProperties() {
        this.redisServer = new RedisServer();
    }

    @Bean
    public void startServer() {
        redisServer.start();
    }

}
