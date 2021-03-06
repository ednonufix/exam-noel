package com.musala.examnoel.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.musala.examnoel.model.Gateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 7/5/21
    Time: 2:24
*/

@Configuration
public class RedisConfiguration {

    private static final String FORMATO_HORA = "yyyy-MM-dd HH:mm:ss";

    ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modulesToInstall(new JavaTimeModule().addSerializer(Date.class, new DateSerializer(Boolean.FALSE, new SimpleDateFormat(FORMATO_HORA)))
                    .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(FORMATO_HORA)))
                    .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(FORMATO_HORA)))
                    .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss"))))
            .failOnEmptyBeans(false)
            .build();

    @Bean("GATEWAY")
    public ReactiveRedisTemplate<String, Gateway> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {

        GenericJackson2JsonRedisSerializer keySerializer = new GenericJackson2JsonRedisSerializer();

        Jackson2JsonRedisSerializer<Gateway> serializer = new Jackson2JsonRedisSerializer<>(Gateway.class);
        serializer.setObjectMapper(mapper);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Gateway> builder = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Gateway> context = builder.value(serializer).hashValue(serializer).hashKey(keySerializer).build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);

    }

}