package com.musala.examnoel.service.impl;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.musala.examnoel.config.ConfigProperties;
import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.PeripheralDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import com.musala.examnoel.model.Gateway;
import com.musala.examnoel.model.Peripheral;
import com.musala.examnoel.service.ExamService;
import com.musala.examnoel.service.error.ExamServiceException;
import com.musala.examnoel.service.error.ExamServiceNotFoundException;
import com.musala.examnoel.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private static final AtomicLong idCounter = new AtomicLong(1);
    private final RedisUtil redisUtil;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private final ConfigProperties configProperties;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<TemplateControllerDto> saveGateway(GatewayDto gatewayDto) {

        if (ObjectUtils.isNotEmpty(gatewayDto.getPeripherals())) {
            gatewayDto.getPeripherals().parallelStream().forEach(x -> x.setUid(idCounter.getAndIncrement()));
        }

        return redisUtil.saveGateway(Gateway.builder()
                        .ip(gatewayDto.getIp())
                        .name(gatewayDto.getName())
                        .uuid(UUID.randomUUID().toString())
                        .peripherals(mapper.map(gatewayDto.getPeripherals(), List.class))
                        .build())
                .thenReturn(TemplateControllerDto.builder().msg(configProperties.getGuardado()).build())
                .onErrorMap(Exception.class, ex -> new ExamServiceException(configProperties.getMsgError()));
    }

    @Override
    public Flux<GatewayDto> listGateway() {
        return redisUtil.listGateway()
                .map(x -> mapper.map(x, GatewayDto.class))
                .onErrorMap(Exception.class, ex -> new ExamServiceException(configProperties.getMsgError()));
    }

    @Override
    public Mono<GatewayDto> getGateway(String uuid) {
        return redisUtil.getGateway(uuid)
                .map(x -> mapper.map(x, GatewayDto.class))
                .onErrorMap(Exception.class, ex -> new ExamServiceNotFoundException(configProperties.getNotFound()));
    }

    @Override
    public Mono<TemplateControllerDto> deletePeripheral(String uuid, Long idPeripheral) {
        return redisUtil.getGateway(uuid)
                .doOnNext(x -> redisUtil.deletePeripheral(uuid, idPeripheral).subscribe())
                .thenReturn(TemplateControllerDto.builder().msg(configProperties.getBorrado()).build())
                .onErrorMap(Exception.class, ex -> new ExamServiceNotFoundException(configProperties.getNotFound()));
    }

    @Override
    public Mono<TemplateControllerDto> addPeripheral(String uuid, PeripheralDto peripheralDto) {

        return redisUtil.getGateway(uuid)
                .doOnNext(x -> peripheralDto.setUid(idCounter.getAndIncrement()))
                .map(x -> mapper.map(peripheralDto, Peripheral.class))
                .doOnNext(x -> redisUtil.addPeripheral(uuid, x).subscribe())
                .thenReturn(TemplateControllerDto.builder().msg(configProperties.getActualizacion()).build())
                .onErrorMap(Exception.class, ex -> new ExamServiceNotFoundException(configProperties.getNotFound()));
    }
}
