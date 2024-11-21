package com.goorm.LocC.store.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.goorm.LocC.store.exception.StoreException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static com.goorm.LocC.store.exception.StoreErrorCode.INVALID_PROVINCE_ENUM;

@Getter
@RequiredArgsConstructor
public enum Province {
    SEOUL("서울", ""),
    GYEONGGI("경기", ""),
    INCHEON("인천", ""),
    GANGWON("강원", ""),
    DAEJEON("대전", ""),
    CHUNGCHEONG("충청", ""),
    JEOLLA("전라", ""),
    GWANGJU("광주", ""),
    GYEONGSANG("경상", ""),
    DAEGU("대구", ""),
    BUSAN("부산", ""),
    ULSAN("울산", ""),
    JEJU("제주", ""),
    ;

    private final String name;
    private final String imageUrl;
    private static Map<String, Province> NAME_TO_ENUM_MAP = new HashMap<>();

    static {
        for (Province province : Province.values()) {
            NAME_TO_ENUM_MAP.put(province.name, province);
        }
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Province fromName(String name) {
        Province province = NAME_TO_ENUM_MAP.get(name);

        if (province == null) {
            throw new StoreException(INVALID_PROVINCE_ENUM);
        }

        return province;
    }
}