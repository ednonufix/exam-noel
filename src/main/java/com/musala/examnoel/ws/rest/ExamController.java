package com.musala.examnoel.ws.rest;

import com.musala.examnoel.dto.GatewayDto;
import com.musala.examnoel.dto.TemplateControllerDto;
import com.musala.examnoel.service.ExamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 14/6/21
    Time: 16:21
*/
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

    @PostMapping("/api/saveGateway")
    public Mono<ResponseEntity<TemplateControllerDto>> almacena(@Valid @RequestBody GatewayDto dto) {
        return examService.saveGateway(dto)
                .map(ResponseEntity::ok);
    }

}
