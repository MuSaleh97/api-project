package com.spring_api.back_end.business.exception;

public class PrayerTimeCalculationException extends RuntimeException {
    public PrayerTimeCalculationException(String message) {
        super(message);
    }

    public PrayerTimeCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
