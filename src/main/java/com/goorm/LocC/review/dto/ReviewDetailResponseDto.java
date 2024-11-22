package com.goorm.LocC.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewDetailResponseDto {

    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;

    @Schema(description = "가게 이름", example = "양지경성 광안 본점")
    private String storeName;

    @Schema(description = "리뷰 내용", example = "맛있어요!")
    private String content;

    @Schema(description = "리뷰 이미지", example = "https://image.jpg")
    private String image;

    @Schema(description = "좋아요 여부", example = "true")
    private boolean isLiked;

    @Schema(description = "좋아요 개수", example = "10")
    private int likeCount;

//    @Schema(description = "북마크 여부", example = "true")
//    private boolean isBookmarked;

//    @Schema(description = "찜 개수", example = "5")
//    private int dibsCount;

//    @Schema(description = "공유 개수", example = "2")
//    private int shareCount;

    @Schema(description = "방문 날짜", example = "2023-10-10")
    private LocalDate visitDate;

    @Schema(description = "카카오지도 URL", example = "https://kakaomap.url")
    private String kakaomapUrl;

    public ReviewDetailResponseDto(Long reviewId, String storeName, String content, String image, boolean isLiked, int likeCount,
                                   boolean isBookmarked, int dibsCount, int shareCount, LocalDate visitDate, String kakaomapUrl) {
        this.reviewId = reviewId;
        this.storeName = storeName;
        this.content = content;
        this.image = image;
        this.isLiked = isLiked;
        this.likeCount = likeCount;
        this.visitDate = visitDate;
        this.kakaomapUrl = kakaomapUrl;
    }
}
