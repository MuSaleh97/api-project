package com.spring_api.back_end.business.exception;

public class PasswordsMatchException extends RuntimeException {
    private static final String ERROR_MSG = "Password and Confirm Password don't match";

    public PasswordsMatchException() {
        super(ERROR_MSG);
    }
}
