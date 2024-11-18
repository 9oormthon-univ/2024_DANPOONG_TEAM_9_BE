package com.goorm.LocC.curation.exception;

import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CurationErrorCode implements BaseErrorCode {

    CURATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CURATION_404_0", "존재하지 않는 큐레이션입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
