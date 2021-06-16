package com.musala.examnoel.util;

import com.musala.examnoel.model.Gateway;
import com.musala.examnoel.model.Peripheral;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Repository
public class RedisUtil {

    private static final String GATEWAY = "GATEWAY";

    @Qualifier("GATEWAY")
    private final ReactiveHashOperations<String, String, Gateway> templateGateway;

    public RedisUtil(@Qualifier("GATEWAY") ReactiveRedisTemplate<String, Gateway> gatewayReactiveRedisTemplate){
        this.templateGateway = gatewayReactiveRedisTemplate.opsForHash(gatewayReactiveRedisTemplate.getSerializationContext());
    }

    public Mono<Void> saveGateway(Gateway gateway) {
        templateGateway.put(GATEWAY, gateway.getUuid(), gateway).subscribe();
        return Mono.empty();
    }

    public Flux<Gateway> listGateway(){
        return templateGateway.values(GATEWAY);
    }

    public Mono<Gateway> getGateway(String uuid){
        return listGateway()
                .filter(x -> x.getUuid().equalsIgnoreCase(uuid))
                .single();
    }

    public Mono<Void> deletePeripheral(String uuid, Long idPeripheral) {
        templateGateway.get(GATEWAY,uuid)
                .flatMap(valor -> {

                    Stream<Peripheral> peripheral = valor.getPeripherals().parallelStream().filter(x-> x.getUid().equals(idPeripheral));

                    if (valor.getPeripherals().size()>1)
                        valor.getPeripherals().removeAll(peripheral.collect(Collectors.toList()));

                    templateGateway.put(GATEWAY, valor.getUuid(), valor).subscribe();
                    return Mono.empty();

                }).subscribe();

        return Mono.empty();
    }

    public Mono<Void> addPeripheral(String uuid, Peripheral peripheral) {
        templateGateway.get(GATEWAY,uuid)
                .flatMap(valor -> {

                    if (valor.getPeripherals().size()<10)
                        valor.getPeripherals().add(peripheral);

                    templateGateway.put(GATEWAY, valor.getUuid(), valor).subscribe();
                    return Mono.empty();

                }).subscribe();

        return Mono.empty();
    }



}
