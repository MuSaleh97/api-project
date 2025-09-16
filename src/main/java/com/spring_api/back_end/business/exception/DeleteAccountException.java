package com.spring_api.back_end.business.exception;

public class DeleteAccountException extends RuntimeException {
    private static final String ERROR_MSG = "This account has already been deleted";

    public DeleteAccountException() {
        super(ERROR_MSG);
    }
}
