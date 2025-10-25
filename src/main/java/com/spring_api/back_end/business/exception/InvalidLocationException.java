package com.spring_api.back_end.business.exception;

public class InvalidLocationException extends RuntimeException {
    public InvalidLocationException(String message) {
        super(message);
    }
}
