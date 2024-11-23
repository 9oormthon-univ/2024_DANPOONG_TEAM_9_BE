package com.goorm.LocC.store.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.goorm.LocC.store.exception.StoreException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static com.goorm.LocC.store.exception.StoreErrorCode.INVALID_CATEGORY_ENUM;

@Getter
@RequiredArgsConstructor
public enum Category {
    FOOD("식품"),
    RESTAURANT("맛집"),
    CAFE("카페"),
    HANDCRAFT("체험/공방"),
    SHOPPING("쇼핑"),
    ONLINE("온라인"),
    CULTURE("문화"),
    BAR("술집"),
    ACCOMMODATION("숙소");

    private final String name;
    private static final Map<String, Category> NAME_TO_ENUM_MAP = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            NAME_TO_ENUM_MAP.put(category.name, category);
        }
    }

    /**
     * 직렬화(객체 -> JSON)시 name 반환
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * 역직렬화(JSON -> 객체)시 Category 반환
     */
    @JsonCreator
    public static Category fromName(String name) {
        Category category = NAME_TO_ENUM_MAP.get(name);

        if (category == null) {
            throw new StoreException(INVALID_CATEGORY_ENUM);
        }

        return category;
    }
}
