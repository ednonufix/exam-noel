package com.musala.examnoel.service.impl;

import com.github.dozermapper.core.Mapper;
import com.musala.examnoel.config.ConfigProperties;
import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.PeripheralDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import com.musala.examnoel.model.Gateway;
import com.musala.examnoel.model.Peripheral;
import com.musala.examnoel.service.ExamService;
import com.musala.examnoel.util.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@Import({
        ExamServiceImpl.class,
        Mapper.class,
        TemplateControllerDto.class,
        ConfigProperties.class
})
public class ExamServiceImplTest {

    @MockBean
    private  RedisUtil redisUtil;

    @MockBean
    private  ExamService service;

    @MockBean
    private  Mapper mapper;

    @MockBean
    private  ConfigProperties configProperties;

    @BeforeEach
     void setUp() {

        service = new ExamServiceImpl(redisUtil,configProperties);

    }

    List<PeripheralDto> peripheralDtoList(){

        List<PeripheralDto> list = new ArrayList<>();

        list.add(PeripheralDto.builder()
                .status(true)
                .uid(20L)
                .vector("Vector1")
                .dateCreated(LocalDate.now())
                .build());

        list.add(PeripheralDto.builder()
                .status(true)
                .uid(21L)
                .vector("Vector2")
                .dateCreated(LocalDate.now())
                .build());

        return list;

    }

    List<Peripheral> peripheralList(){

        List<Peripheral> list = new ArrayList<>();

        list.add(Peripheral.builder()
                .status(true)
                .uid(20L)
                .vector("Vector1")
                .dateCreated(LocalDate.now())
                .build());

        list.add(Peripheral.builder()
                .status(true)
                .uid(21L)
                .vector("Vector2")
                .dateCreated(LocalDate.now())
                .build());

        return list;

    }

    Peripheral mockPeripheral(){

        return Peripheral.builder()
                .status(Boolean.TRUE)
                .dateCreated(LocalDate.now())
                .uid(1L)
                .vector("Vector")
                .build();
    }

    PeripheralDto mockPeripheralDto(){

        return PeripheralDto.builder()
                .status(Boolean.TRUE)
                .dateCreated(LocalDate.now())
                .uid(1L)
                .vector("Vector")
                .build();
    }

    GatewayDto mockGatewayDto(){

        return GatewayDto.builder()
                .uuid(UUID.randomUUID().toString())
                .ip("10.90.25.10")
                .name("CISCO")
                .peripherals(peripheralDtoList())
                .build();
    }

    Gateway mockGateway(){

        return Gateway.builder()
                .uuid(UUID.randomUUID().toString())
                .ip("10.90.25.10")
                .name("CISCO")
                .peripherals(peripheralList())
                .build();
    }

    @Test
    void addGateway() {

        Mockito.when(redisUtil.saveGateway(Mockito.any())).thenReturn(Mono.just(Boolean.TRUE));
        Mockito.when(mapper.map(Mockito.any(),Mockito.any())).thenReturn(mockGateway());
        Mockito.when(configProperties.getGuardado()).thenReturn("Message");
        Mockito.when(configProperties.getMsgError()).thenReturn("Message");

        StepVerifier.create(service.saveGateway(mockGatewayDto()))
                .expectNext()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void listGateway() {

        Mockito.when(redisUtil.listGateway()).thenReturn(Flux.just(mockGateway()));
        Mockito.when(mapper.map(Mockito.any(),Mockito.any())).thenReturn(mockGatewayDto());
        Mockito.when(configProperties.getMsgError()).thenReturn("Message");

        StepVerifier.create(service.listGateway())
                .expectNext()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getGateway() {

        Mockito.when(redisUtil.getGateway(Mockito.any())).thenReturn(Mono.just(mockGateway()));
        Mockito.when(mapper.map(Mockito.any(),Mockito.any())).thenReturn(mockGatewayDto());
        Mockito.when(configProperties.getNotFound()).thenReturn("Message");

        StepVerifier.create(service.getGateway(UUID.randomUUID().toString()))
                .expectNext()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void deletePeripheral() {

        Mockito.when(redisUtil.getGateway(Mockito.any())).thenReturn(Mono.just(mockGateway()));
        Mockito.when(redisUtil.deletePeripheral(Mockito.any(),Mockito.anyLong())).thenReturn(Mono.just(Boolean.TRUE));
        Mockito.when(configProperties.getBorrado()).thenReturn("Message");
        Mockito.when(configProperties.getNotFound()).thenReturn("Message");

        StepVerifier.create(service.deletePeripheral(UUID.randomUUID().toString(),1L))
                .expectNext()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void addPeripheral() {

        Mockito.when(redisUtil.getGateway(Mockito.any())).thenReturn(Mono.just(mockGateway()));
        Mockito.when(redisUtil.addPeripheral(Mockito.any(),Mockito.any())).thenReturn(Mono.just(Boolean.TRUE));
        Mockito.when(mapper.map(Mockito.any(),Mockito.any())).thenReturn(mockPeripheral());
        Mockito.when(configProperties.getActualizacion()).thenReturn("Message");
        Mockito.when(configProperties.getNotFound()).thenReturn("Message");

        StepVerifier.create(service.addPeripheral(UUID.randomUUID().toString(),mockPeripheralDto()))
                .expectNext()
                .expectNextCount(1)
                .verifyComplete();
    }


}
