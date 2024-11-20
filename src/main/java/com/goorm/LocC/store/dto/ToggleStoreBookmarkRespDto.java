package com.goorm.LocC.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ToggleStoreBookmarkRespDto {

    @Schema(description = "총 가게 북마크 수")
    private int count;
    @Schema(description = "해당 유저의 가게 북마크 여부")
    private boolean isBookmarked;

    @Builder
    public ToggleStoreBookmarkRespDto(int count, boolean isBookmarked) {
        this.count = count;
        this.isBookmarked = isBookmarked;
    }
}
