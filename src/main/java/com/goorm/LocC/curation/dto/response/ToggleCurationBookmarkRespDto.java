package com.goorm.LocC.curation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ToggleCurationBookmarkRespDto {

    @Schema(description = "해당 유저의 가게 북마크 여부")
    private boolean isBookmarked;

    public ToggleCurationBookmarkRespDto(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }
}
