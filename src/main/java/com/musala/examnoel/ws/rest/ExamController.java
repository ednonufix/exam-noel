package com.musala.examnoel.ws.rest;

import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.PeripheralDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import com.musala.examnoel.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Store a Gateway")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Gateway to store",
            content = @Content(schema=@Schema(implementation = GatewayDto.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gateway successfully created",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
    })
    @PostMapping("/api/gatewayOps")
    public Mono<ResponseEntity<TemplateControllerDto>> almacena(@Valid @RequestBody GatewayDto dto) {
        return examService.saveGateway(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Gets the list of Gateway")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gateways created",
                    content = { @Content(mediaType = "application/json",schema = @Schema(allOf = GatewayDto.class, description = "List of Gateways")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class))})
    })
    @GetMapping("/api/gatewayOps")
    public Mono<ResponseEntity<Flux<GatewayDto>>> listGateway() {
        return Mono.just(ResponseEntity.ok(examService.listGateway()));
    }

    @Operation(summary = "Get a Gateway given its UUID")
    @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Gateway UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gateway obtained",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = GatewayDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Gateway not found",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class))})
    })
    @GetMapping("/api/gatewayOps/{uuid}")
    public Mono<ResponseEntity<GatewayDto>> getGateway(@PathVariable(value = "uuid")String uuid) {
        return examService.getGateway(uuid)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Deletes a Peripheral given its ID and the UUID of its Gateway")
    @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Gateway UUID")
    @Parameter(in = ParameterIn.PATH, name = "id", description = "Peripheral ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peripheral successfully removed",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Gateway not found",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class))})
    })
    @DeleteMapping("/api/gatewayOps/{uuid}/{id}")
    public Mono<ResponseEntity<TemplateControllerDto>> deletePeripheral(@PathVariable(value = "uuid")String uuid, @PathVariable(value = "id") Long id) {
        return examService.deletePeripheral(uuid,id)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Add a Peripheral via the UUID of the Gateway")
    @Parameter(in = ParameterIn.PATH, name = "uuid", description = "UUID del Gateway")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peripheral successfully added",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Gateway not found",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = TemplateControllerDto.class))})
    })
    @PutMapping("/api/gatewayOps/{uuid}")
    public Mono<ResponseEntity<TemplateControllerDto>> addPeripheral(@PathVariable(value = "uuid")String uuid, @RequestBody @Valid PeripheralDto dto) {
        return examService.addPeripheral(uuid,dto)
                .map(ResponseEntity::ok);
    }

}
