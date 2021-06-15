package com.musala.examnoel.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 13/5/21
    Time: 3:50
*/
@Configuration
@Data
public class RedisProperties {

    private RedisServer redisServer;

    public RedisProperties() {
        this.redisServer = new RedisServer();
    }

    @Bean
    public void startServer(){
        redisServer.start();
    }

}
