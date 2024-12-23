package com.aldeamo.microservice1.exception;

public class InvalidMessageTypeException extends RuntimeException {
    public InvalidMessageTypeException(String message) {
        super(message);
    }
}