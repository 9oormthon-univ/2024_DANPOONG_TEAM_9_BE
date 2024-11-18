package com.goorm.LocC.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드를 제외하고 반환
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

    private final static String SUCCESS_CODE = "200";

    public static<T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(SUCCESS_CODE)
                .data(data)
                .build();
    }

    public static<T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure(BaseErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> failure(String code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
