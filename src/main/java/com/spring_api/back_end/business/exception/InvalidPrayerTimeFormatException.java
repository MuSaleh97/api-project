package com.spring_api.back_end.business.exception;

public class InvalidPrayerTimeFormatException extends RuntimeException {
    public InvalidPrayerTimeFormatException(String message) {
        super(message);
    }

    public InvalidPrayerTimeFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
