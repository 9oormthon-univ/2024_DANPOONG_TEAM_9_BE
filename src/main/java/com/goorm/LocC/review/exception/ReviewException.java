package com.goorm.LocC.review.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;

@Getter
public class ReviewException extends BaseException {

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}
