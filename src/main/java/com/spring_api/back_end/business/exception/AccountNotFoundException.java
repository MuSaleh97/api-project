package com.spring_api.back_end.business.exception;

public class AccountNotFoundException extends RuntimeException {
    private static final String ERROR_MSG = "Your account was not found. Please check your account information";

    public AccountNotFoundException() {
        super(ERROR_MSG);
    }
}
