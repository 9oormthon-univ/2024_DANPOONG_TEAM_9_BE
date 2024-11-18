package com.goorm.LocC.store.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.goorm.LocC.store.exception.StoreException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static com.goorm.LocC.store.domain.Province.GANGWON;
import static com.goorm.LocC.store.exception.StoreErrorCode.INVALID_CITY_ENUM;

@Getter
@RequiredArgsConstructor
public enum City {
    CHUNCHEON("춘천시", GANGWON),
    WONJU("원주시", GANGWON),
    GANGNEUNG("강릉시", GANGWON),
    DONGHAE("동해시", GANGWON),
    TAEBAEK("태백시", GANGWON),
    SOKCHO("속초시", GANGWON),
    SAMCHEOK("삼척시", GANGWON),
    HONGCHEON("홍천군", GANGWON),
    HOENGSEONG("횡성군", GANGWON),
    YEONGWOL("영월군", GANGWON),
    PYEONGCHANG("평창군", GANGWON),
    JEONGSEON("정선군", GANGWON),
    CHEORWON("철원군", GANGWON),
    HWACHEON("화천군", GANGWON),
    YANGGU("양구군", GANGWON),
    INJE("인제군", GANGWON),
    GOSEONG("고성군", GANGWON),
    YANGYANG("양양군", GANGWON),
    ;

    private final String name;
    private final Province province;
    private static final Map<String, City> NAME_TO_ENUM_MAP = new HashMap<>();

    static {
        for (City city : City.values()) {
            NAME_TO_ENUM_MAP.put(city.name, city);
        }
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static City fromName(String name) {
        City city = NAME_TO_ENUM_MAP.get(name);

        if (city == null) {
            throw new StoreException(INVALID_CITY_ENUM);
        }
        return city;
    }
}
