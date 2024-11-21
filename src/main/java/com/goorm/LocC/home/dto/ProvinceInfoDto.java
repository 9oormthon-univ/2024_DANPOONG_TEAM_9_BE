package com.goorm.LocC.home.dto;

import com.goorm.LocC.store.domain.Province;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProvinceInfoDto {

    private Province province;
    private String imageUrl;

    @Builder
    public ProvinceInfoDto(Province province, String imageUrl) {
        this.province = province;
        this.imageUrl = imageUrl;
    }

    public static ProvinceInfoDto of(Province province) {
        return ProvinceInfoDto.builder()
                .province(province)
                .imageUrl(province.getImageUrl())
                .build();
    }
}
