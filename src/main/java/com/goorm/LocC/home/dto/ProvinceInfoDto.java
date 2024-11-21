package com.goorm.LocC.home.dto;

import com.goorm.LocC.store.domain.Province;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProvinceInfoDto {

    @Schema(description = "특별시/광역시/도", example = "강원")
    private Province province;
    @Schema(description = "이미지 URL", example = "https://image.jpg")
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
