package com.spring_api.back_end.business.exception;

public class OTPSendErrorException extends RuntimeException {
    private static final String ERROR_MSG = "An internal error occurred. We were unable to send OTP to your email. Please try again.";

    public OTPSendErrorException() {
        super(ERROR_MSG);
    }
}
