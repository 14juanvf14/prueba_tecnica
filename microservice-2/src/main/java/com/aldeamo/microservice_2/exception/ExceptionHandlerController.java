package com.aldeamo.microservice_2.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlerController {

    //No hay excepciones tratadas en el controlador creado

    @Data
    @Builder
    public static class ErrorResponse {
        private int status;
        private String message;
    }
}