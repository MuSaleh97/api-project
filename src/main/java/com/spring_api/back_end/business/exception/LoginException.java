package com.spring_api.back_end.business.exception;

public class LoginException extends RuntimeException {
    private static final String ERROR_MSG = "The username or password is incorrect";

    public LoginException() {
        super(ERROR_MSG);
    }
}
