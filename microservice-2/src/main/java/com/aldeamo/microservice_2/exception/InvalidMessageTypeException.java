package com.aldeamo.microservice_2.exception;

public class InvalidMessageTypeException extends RuntimeException {
    public InvalidMessageTypeException(String message) {
        super(message);
    }
}