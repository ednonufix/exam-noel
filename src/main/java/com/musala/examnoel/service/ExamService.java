package com.musala.examnoel.service;

import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.PeripheralDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ExamService {

    Mono<TemplateControllerDto> saveGateway(GatewayDto gatewayDto);
    Mono<TemplateControllerDto> deletePeripheral(String uuid, Long idPeripheral);
    Mono<TemplateControllerDto> addPeripheral(String uuid, PeripheralDto peripheralDto);
    Flux<GatewayDto> listGateway();
    Mono<GatewayDto> getGateway(String uuid);

}
