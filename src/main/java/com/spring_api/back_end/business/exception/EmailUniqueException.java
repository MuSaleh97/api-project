package com.spring_api.back_end.business.exception;

public class EmailUniqueException extends RuntimeException {
    private static final String ERROR_MSG = "This email is already in use";

    public EmailUniqueException() {
        super(ERROR_MSG);
    }
}
