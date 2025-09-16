package com.spring_api.back_end.business.exception;

public class ChangeEmailException extends RuntimeException {
    private static final String ERROR_MSG = "The data has been changed successfully, including the email. You must log out to reactivate the new email.";

    public ChangeEmailException() {
        super(ERROR_MSG);
    }
}
