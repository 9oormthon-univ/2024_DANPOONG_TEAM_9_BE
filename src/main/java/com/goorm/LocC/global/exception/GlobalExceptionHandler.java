package com.goorm.LocC.global.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.goorm.LocC.auth.exception.AuthException;
import com.goorm.LocC.global.common.dto.ApiResponse;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.review.exception.ReviewException;
import com.goorm.LocC.searchHistory.exception.SearchHistoryException;
import com.goorm.LocC.store.exception.StoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();

        log.warn("HttpMessageNotReadable Exception: {}", e.getMessage());

        if (cause instanceof ValueInstantiationException exception) {
            Throwable innerCause = exception.getCause(); // 내부 예외 확인
            if (innerCause instanceof BaseException baseException) {
                return baseException.getErrorCode().toResponseEntity();
            }
        }

//        if (cause instanceof InvalidFormatException exception) {
//            Class<?> targetType = exception.getTargetType();
//            if (targetType == Province.class) {
//                return INVALID_PROVINCE_ENUM.toResponseEntity();
//            } else if (targetType == Category.class) {
//                return INVALID_CATEGORY_ENUM.toResponseEntity();
//            } else if (targetType == City.class) {
//                return INVALID_CITY_ENUM.toResponseEntity();
//            }
//        }

        return ResponseEntity.badRequest()
                .body(ApiResponse.failure("400", "유효하지 않은 요청입니다."));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberException(MemberException e) {
        log.warn("Member Exception: {}", e.getMessage());

        return e.getErrorCode().toResponseEntity();
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthException(AuthException e) {
        log.warn("Auth Exception: {}", e.getMessage());

        return e.getErrorCode().toResponseEntity();
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ApiResponse<Void>> handleReviewException(ReviewException e) {
        log.warn("Review Exception: {}", e.getMessage());

        return e.getErrorCode().toResponseEntity();
    }

    @ExceptionHandler(StoreException.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreException(StoreException e) {
        log.warn("Store Exception: {}", e.getMessage());

        return e.getErrorCode().toResponseEntity();
    }

    @ExceptionHandler(SearchHistoryException.class)
    public ResponseEntity<ApiResponse<Void>> handleSearchHistoryException(SearchHistoryException e) {
        log.warn("SearchHistory Exception: {}", e.getMessage());

        return e.getErrorCode().toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.warn(e.getMessage(), e);

        HttpStatus status = INTERNAL_SERVER_ERROR;
        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure(String.valueOf(status.value()), status.getReasonPhrase()));
    }
}
