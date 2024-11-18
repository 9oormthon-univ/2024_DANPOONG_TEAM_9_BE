package com.goorm.LocC.review.exception;

import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_404_0", "존재하지 않는 리뷰입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
