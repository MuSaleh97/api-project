package com.spring_api.back_end.business.exception;

public class OTPVerificationException extends RuntimeException {
    private static final String ERROR_MSG = "Please check the OTP code";

    public OTPVerificationException() {
        super(ERROR_MSG);
    }
}
