package com.goorm.LocC.global.exception;

import com.goorm.LocC.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BaseErrorCode {

    HttpStatus getStatus();
    String getCode();
    String getMessage();

    default ApiResponse<Void> toApiResponse() {
        return ApiResponse.failure(this);
    }

    default ResponseEntity<ApiResponse<Void>> toResponseEntity() {
        return ResponseEntity
                .status(getStatus())
                .body(toApiResponse());
    }
}
