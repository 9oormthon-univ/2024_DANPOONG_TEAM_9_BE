package com.goorm.LocC.curation.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;

@Getter
public class CurationException extends BaseException {

    public CurationException(CurationErrorCode errorCode) {
        super(errorCode);
    }
}
