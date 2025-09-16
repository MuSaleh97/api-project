package com.spring_api.back_end.business.exception;

public class AccountIsNotVerifiedException extends RuntimeException {
    private static final String ERROR_MSG = "The account is not verified";

    public AccountIsNotVerifiedException() {
        super(ERROR_MSG);
    }
}
