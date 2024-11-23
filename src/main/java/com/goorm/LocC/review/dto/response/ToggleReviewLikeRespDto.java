package com.goorm.LocC.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ToggleReviewLikeRespDto {

    @Schema(description = "총 리뷰 좋아요 수", example = "123")
    private int count;
    @Schema(description = "해당 유저의 리뷰 좋아요 여부")
    private boolean isBookmarked;

    @Builder
    public ToggleReviewLikeRespDto(int count, boolean isBookmarked) {
        this.count = count;
        this.isBookmarked = isBookmarked;
    }
}
