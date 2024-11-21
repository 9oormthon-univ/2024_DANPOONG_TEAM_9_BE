package com.goorm.LocC.store.dto;

import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BenefitStoreInfoDto {

    @Schema(description = "가게 ID", example = "1")
    private Long storeId;
    @Schema(description = "가게 이름", example = "양지경성 광안 본점")
    private String name;
    @Schema(description = "가게 평점", example = "4.4")
    private float rating;
    @Schema(description = "특별시/광역시/도", example = "강원")
    private Province province;
    @Schema(description = "시/군/구", example = "강릉시")
    private City city;
    @Schema(description = "이미지 URL", example = "https://image.jpg")
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