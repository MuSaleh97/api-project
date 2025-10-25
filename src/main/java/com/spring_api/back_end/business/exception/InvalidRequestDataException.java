package com.spring_api.back_end.business.exception;

public class InvalidRequestDataException extends RuntimeException {
    public InvalidRequestDataException(String message) {
        super(message);
    }
}
