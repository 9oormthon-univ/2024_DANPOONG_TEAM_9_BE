package com.goorm.LocC.searchHistory.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;

@Getter
public class SearchHistoryException extends BaseException {

    public SearchHistoryException(SearchHistoryErrorCode errorCode) {
        super(errorCode);
    }
}
