package com.musala.examnoel.util;

import com.musala.examnoel.model.Gateway;
import com.musala.examnoel.model.Peripheral;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 7/5/21
    Time: 2:34
*/
@Repository
public class RedisUtil {

    private final static String GATEWAY = "GATEWAY";
    private final static String PERIPHERAL = "PERIPHERAL";

    @Qualifier("GATEWAY")
    private final ReactiveHashOperations<String, Long, Gateway> templateGateway;

    @Qualifier("PERIPHERAL")
    private final ReactiveHashOperations<String, Long, Peripheral> templatePeripheral;

    public RedisUtil(@Qualifier("GATEWAY") ReactiveRedisTemplate<String, Gateway> gatewayReactiveRedisTemplate,
                     @Qualifier("PERIPHERAL")ReactiveRedisTemplate<String, Peripheral> peripheralReactiveRedisTemplate) {
        this.templateGateway = gatewayReactiveRedisTemplate.opsForHash(gatewayReactiveRedisTemplate.getSerializationContext());
        this.templatePeripheral = peripheralReactiveRedisTemplate.opsForHash(peripheralReactiveRedisTemplate.getSerializationContext());
    }

    public Mono<Void> saveGateway(Long idGateway,Gateway gateway) {
        templateGateway.put(GATEWAY, idGateway, gateway).subscribe();
        return Mono.empty();
    }



}
