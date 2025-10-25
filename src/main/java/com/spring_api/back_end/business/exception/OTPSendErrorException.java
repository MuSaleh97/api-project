package com.spring_api.back_end.business.exception;

public class OTPSendErrorException extends RuntimeException {
    private static final String DEFAULT_ERROR_MSG = "An internal error occurred. We were unable to send OTP. Please try again.";

    public OTPSendErrorException() {
        super(DEFAULT_ERROR_MSG);
    }

    public OTPSendErrorException(String message) {
        super(message);
    }
}
