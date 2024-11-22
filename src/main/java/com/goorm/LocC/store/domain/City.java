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
    CHUNCHEON("춘천시", Province.GANGWON),
    WONJU("원주시", Province.GANGWON),
    GANGNEUNG("강릉시", Province.GANGWON),
    DONGHAE("동해시", Province.GANGWON),
    TAEBAEK("태백시", Province.GANGWON),
    SOKCHO("속초시", Province.GANGWON),
    SAMCHEOK("삼척시", Province.GANGWON),
    HONGCHEON("홍천군", Province.GANGWON),
    HOENGSEONG("횡성군", Province.GANGWON),
    YEONGWOL("영월군", Province.GANGWON),
    PYEONGCHANG("평창군", Province.GANGWON),
    JEONGSEON("정선군", Province.GANGWON),
    CHEORWON("철원군", Province.GANGWON),
    HWACHEON("화천군", Province.GANGWON),
    YANGGU("양구군", Province.GANGWON),
    INJE("인제군", Province.GANGWON),
    GOSEONG("고성군", Province.GANGWON),
    YANGYANG("양양군", Province.GANGWON),
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
