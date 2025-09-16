package com.spring_api.back_end.business.exception;

public class PhoneUniqueException extends RuntimeException {
    private static final String ERROR_MSG = "This mobile number is already in use";

    public PhoneUniqueException() {
        super(ERROR_MSG);
    }
}
