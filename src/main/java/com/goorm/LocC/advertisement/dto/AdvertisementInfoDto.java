package com.goorm.LocC.advertisement.dto;

import com.goorm.LocC.advertisement.domain.Advertisement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AdvertisementInfoDto {

    @Schema(description = "광고 ID", example = "1")
    private Long advertisementId;
    @Schema(description = "광고 제목", example = "쏘카 3시간 할인 지역 보기")
    private String title;
    @Schema(description = "광고 부제목", example = "이동할 때부터 지칠 수는 없으니까 친구들이랑 편하게")
    private String subtitle;
    @Schema(description = "광고 이미지 URL", example = "https://image.jpg")
    private String imageUrl;

    @Builder
    public AdvertisementInfoDto(Long advertisementId, String title, String subtitle, String imageUrl) {
        this.advertisementId = advertisementId;
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }

    public static AdvertisementInfoDto from(Advertisement advertisement) {
        return AdvertisementInfoDto.builder()
                .advertisementId(advertisement.getAdvertisementId())
                .title(advertisement.getTitle())
                .subtitle(advertisement.getSubtitle())
                .imageUrl(advertisement.getImageUrl())
                .build();
    }
}
