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
    SEOUL("서울", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/SEOUL.png"),
    GYEONGGI("경기", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/GYEONGGI.png"),
    INCHEON("인천", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/INCHEON.png"),
    GANGWON("강원", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/GANGWON.png"),
    DAEJEON("대전", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/DAEJEON.png"),
    CHUNGCHEONG("충청", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/CHUNGCHEONG.png"),
    JEOLLA("전라", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/JEOLLA.png"),
    GWANGJU("광주", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/GWANGJU.png"),
    GYEONGSANG("경상", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/GYEONGSANG.png"),
    DAEGU("대구", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/DAEGU.png"),
    BUSAN("부산", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/BUSAN.png"),
    ULSAN("울산", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/ULSAN.png"),
    JEJU("제주", "https://locc-bucket.s3.ap-northeast-2.amazonaws.com/province_icon/JEJU.png");
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