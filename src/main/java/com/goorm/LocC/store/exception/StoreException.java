package com.goorm.LocC.store.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;

@Getter
public class StoreException extends BaseException {

    public StoreException(StoreErrorCode errorCode) {
        super(errorCode);
    }
}
