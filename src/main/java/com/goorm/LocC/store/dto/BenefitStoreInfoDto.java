package com.goorm.LocC.store.dto;

import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BenefitStoreInfoDto {

    private Long storeId;
    private String name;
    private float rating;
    private Province province;
    private City city;
    private String imageUrl;

    public BenefitStoreInfoDto(Long storeId, String name, float rating, Province province, City city, String imageUrl) {
        this.storeId = storeId;
        this.name = name;
        this.rating = Math.round(rating * 100) / 100.0f;;
        this.province = province;
        this.city = city;
        this.imageUrl = imageUrl;
    }
}