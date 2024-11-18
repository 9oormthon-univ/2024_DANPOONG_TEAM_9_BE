package com.goorm.LocC.advertisement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AdvertisementException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    public AdvertisementException(AdvertisementErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
    }
}
