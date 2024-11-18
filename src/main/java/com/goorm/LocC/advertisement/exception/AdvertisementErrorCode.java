package com.goorm.LocC.advertisement.exception;

import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AdvertisementErrorCode implements BaseErrorCode {

    ADVERTISEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ADVERTISEMENT_404_0", "존재하지 않는 광고입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
