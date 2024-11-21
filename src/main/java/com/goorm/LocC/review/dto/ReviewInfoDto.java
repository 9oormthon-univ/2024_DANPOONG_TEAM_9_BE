package com.goorm.LocC.review.dto;

import com.goorm.LocC.store.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewInfoDto {

    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;
    @Schema(description = "리뷰어 닉네임", example = "미르미")
    private String reviewerName;
    @Schema(description = "가게 이름", example = "양지경성 광안 본점")
    private String storeName;
    @Schema(description = "카테고리", example = "맛집")
    private Category category;
    @Schema(description = "가게 평점", example = "4.40")
    private float rating;
    @Schema(description = "리뷰 수", example = "274")
    private int reviewCount;
    @Schema(description = "리뷰 이미지", example = "https://image.jpg")
    private String imageUrl;

    public ReviewInfoDto(Long reviewId, String reviewerName, String storeName, Category category, float rating, int reviewCount, String imageUrl) {
        this.reviewId = reviewId;
        this.reviewerName = reviewerName;
        this.storeName = storeName;
        this.category = category;
        this.rating = Math.round(rating * 100) / 100.0f;
        this.reviewCount = reviewCount;
        this.imageUrl = imageUrl;
    }
}
