package com.goorm.LocC.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreDetailDto {

    @Schema(description = "가게 기본 정보")
    private StoreInfoDto storeInfo;

    @Schema(description = "북마크 여부", example = "true")
    private Boolean isBookmarked;

    @Schema(description = "현재 위치로부터의 거리 (km)", example = "1.2")
    private Float distance;

    @Schema(description = "스토어 요약", example = "서울 최고의 카페입니다.")
    private String summary;
}
