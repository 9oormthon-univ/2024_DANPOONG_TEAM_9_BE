package com.goorm.LocC.advertisement.dto;

import com.goorm.LocC.advertisement.domain.Advertisement;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AdvertisementInfoDto {

    private Long advertisementId;
    private String title;
    private String subtitle;
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
