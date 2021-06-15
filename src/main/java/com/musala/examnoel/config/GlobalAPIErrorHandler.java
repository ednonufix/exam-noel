package com.musala.examnoel.config;

import com.musala.examnoel.dto.TemplateControllerDto;
import com.musala.examnoel.service.error.ExamServiceException;
import com.musala.examnoel.service.error.ExamServiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
    User:Eduardo Noel<enoel@soaint.com>
    Date: 15/5/21
    Time: 23:16
*/
@RestControllerAdvice
public class GlobalAPIErrorHandler {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ExamServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity errorPeticion(ExamServiceException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new TemplateControllerDto(ex.getMessage()));
	}

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ExamServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity errorNoEncontrado(ExamServiceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new TemplateControllerDto(ex.getMessage()));
    }

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity internalError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new TemplateControllerDto(ex.getMessage()));
	}

}
