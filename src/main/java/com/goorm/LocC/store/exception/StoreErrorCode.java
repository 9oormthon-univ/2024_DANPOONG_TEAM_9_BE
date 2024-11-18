package com.goorm.LocC.store.exception;

import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    INVALID_PROVINCE_ENUM(HttpStatus.BAD_REQUEST, "STORE_400_0", "유효하지 않은 특별시/광역시/도 값입니다."),
    INVALID_CITY_ENUM(HttpStatus.BAD_REQUEST, "STORE_400_1", "유효하지 않은 시/군/구 값입니다."),
    INVALID_CATEGORY_ENUM(HttpStatus.BAD_REQUEST, "STORE_400_2", "유효하지 않은 카테고리 값입니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_404_0", "존재하지 않는 가게입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
