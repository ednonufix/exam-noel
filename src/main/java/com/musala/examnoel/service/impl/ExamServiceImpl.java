package com.musala.examnoel.service.impl;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.musala.examnoel.config.ConfigProperties;
import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import com.musala.examnoel.model.Gateway;
import com.musala.examnoel.service.ExamService;
import com.musala.examnoel.service.error.ExamServiceException;
import com.musala.examnoel.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 7/5/21
    Time: 4:37
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private static final AtomicLong idCounter = new AtomicLong(1);
    private final RedisUtil redisUtil;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private ConfigProperties configProperties;

    @Autowired
    public void setConfigProperties(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Override
    public Mono<TemplateControllerDto> saveGateway(GatewayDto gatewayDto) {

       gatewayDto.getPeripherals().forEach(x-> x.setUid(idCounter.getAndIncrement()));

        return redisUtil.saveGateway(idCounter.getAndIncrement(),Gateway.builder()
                .ip(gatewayDto.getIp())
                .name(gatewayDto.getName())
                .uuid(UUID.randomUUID().toString())
                .peripherals(mapper.map(gatewayDto.getPeripherals(), List.class))
                .build())
                .flatMap(x-> Mono.just(TemplateControllerDto.builder().msg(configProperties.getGuardado()).build()))
                .onErrorMap(Exception.class, ex -> new ExamServiceException(configProperties.getMsgError()));
    }
}
