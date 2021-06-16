package com.musala.examnoel.ws.rest;

import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.PeripheralDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import com.musala.examnoel.service.ExamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Exam-Controller")
public class ExamController {

    ExamService examService;

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/api/gatewayOps")
    public Mono<ResponseEntity<TemplateControllerDto>> almacena(@Valid @RequestBody GatewayDto dto) {
        return examService.saveGateway(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/api/gatewayOps")
    public Mono<ResponseEntity<Flux<GatewayDto>>> listGateway() {
        return Mono.just(ResponseEntity.ok(examService.listGateway()));
    }

    @GetMapping("/api/gatewayOps/{uuid}")
    public Mono<ResponseEntity<GatewayDto>> getGateway(@PathVariable(value = "uuid")String uuid) {
        return examService.getGateway(uuid)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/gatewayOps/{uuid}/{id}")
    public Mono<ResponseEntity<TemplateControllerDto>> deletePeripheral(@PathVariable(value = "uuid")String uuid, @PathVariable(value = "id") Long id) {
        return examService.deletePeripheral(uuid,id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/api/gatewayOps/{uuid}")
    public Mono<ResponseEntity<TemplateControllerDto>> addPeripheral(@PathVariable(value = "uuid")String uuid, @RequestBody @Valid PeripheralDto dto) {
        return examService.addPeripheral(uuid,dto)
                .map(ResponseEntity::ok);
    }

}
