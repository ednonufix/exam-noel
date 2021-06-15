package com.musala.examnoel.service.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExamServiceNotFoundException extends Exception {

    public ExamServiceNotFoundException(String message) {
        super(message);
    }

    public ExamServiceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}