package com.goorm.LocC.searchHistory.exception;

import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SearchHistoryErrorCode implements BaseErrorCode {

    SEARCH_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "SEARCH_HISTORY_404_0", "존재하지 않는 검색 기록입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
