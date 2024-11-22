package com.goorm.LocC.store.dto;

import com.goorm.LocC.curation.dto.CurationStoreInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoreDetailDto {

    @Schema(description = "가게 기본 정보")
    private CurationStoreInfoDto storeInfo;

    @Schema(description = "북마크 여부", example = "true")
    private boolean isBookmarked;

//    @Schema(description = "현재 위치로부터의 거리 (km)", example = "1.2")
//    private Float distance;

    @Schema(description = "큐레이션 가게 소개", example = "서울 최고의 카페입니다.")
    private String summary;

    @Builder
    public StoreDetailDto(CurationStoreInfoDto storeInfo, Boolean isBookmarked, String summary) {
        this.storeInfo = storeInfo;
        this.isBookmarked = isBookmarked;
        this.summary = summary;
    }
}
