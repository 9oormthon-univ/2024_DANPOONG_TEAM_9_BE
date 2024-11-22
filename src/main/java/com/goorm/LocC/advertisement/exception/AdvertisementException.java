package com.goorm.LocC.advertisement.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;

@Getter
public class AdvertisementException extends BaseException {

    public AdvertisementException(AdvertisementErrorCode errorCode) {
        super(errorCode);
    }
}
