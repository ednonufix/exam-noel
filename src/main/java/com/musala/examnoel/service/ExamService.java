package com.musala.examnoel.service;

import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import reactor.core.publisher.Mono;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 7/5/21
    Time: 4:35
*/
public interface ExamService {

    public Mono<TemplateControllerDto> saveGateway(GatewayDto gatewayDto);


}
