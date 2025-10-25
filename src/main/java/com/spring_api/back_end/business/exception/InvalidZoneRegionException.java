package com.spring_api.back_end.business.exception;

public class InvalidZoneRegionException extends RuntimeException {
    public InvalidZoneRegionException(String message) {
        super(message);
    }
}
