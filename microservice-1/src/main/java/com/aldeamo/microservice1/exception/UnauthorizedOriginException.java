package com.aldeamo.microservice1.exception;

public class UnauthorizedOriginException extends RuntimeException {
    public UnauthorizedOriginException(String message) {
        super(message);
    }
}
