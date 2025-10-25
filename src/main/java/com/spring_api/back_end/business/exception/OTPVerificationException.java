package com.spring_api.back_end.business.exception;

public class OTPVerificationException extends RuntimeException {
    private static final String DEFAULT_ERROR_MSG = "Please check the OTP code";

    public OTPVerificationException() {
        super(DEFAULT_ERROR_MSG);
    }

    public OTPVerificationException(String message) {
        super(message);
    }
}
