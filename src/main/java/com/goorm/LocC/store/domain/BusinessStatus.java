package com.goorm.LocC.store.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.goorm.LocC.store.exception.StoreException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static com.goorm.LocC.store.exception.StoreErrorCode.INVALID_BUSINESS_STATUS_ENUM;

@Getter
@RequiredArgsConstructor
public enum BusinessStatus {
    OPEN("영업중"),
    CLOSE("영업종료"),
    HOLIDAY("휴무일"),
    ;

    private final String name;
    private final static Map<String, BusinessStatus> NAME_TO_ENUM_MAP = new HashMap<>();

    static {
        for (BusinessStatus businessStatus : BusinessStatus.values()) {
            NAME_TO_ENUM_MAP.put(businessStatus.name, businessStatus);
        }
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static BusinessStatus fromName(String name) {
        BusinessStatus businessStatus = NAME_TO_ENUM_MAP.get(name);

        if (businessStatus == null) {
            throw new StoreException(INVALID_BUSINESS_STATUS_ENUM);
        }

        return businessStatus;
    }

    public static BusinessStatus checkBusinessStatus(boolean isHoliday, LocalTime openTime, LocalTime closeTime) {
        if (isHoliday) {
            return HOLIDAY;
        }
        // openTime 또는 closeTime이 null인 경우 CLOSE 상태 반환
        if (openTime == null || closeTime == null) {
            return CLOSE;
        }
        LocalTime now = LocalTime.now();
        return now.isAfter(openTime) && now.isBefore(closeTime) ? OPEN : CLOSE;
    }



}


