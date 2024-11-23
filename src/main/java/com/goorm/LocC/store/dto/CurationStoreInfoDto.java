package com.goorm.LocC.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.LocC.store.domain.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CurationStoreInfoDto {

        @Schema(description = "가게 ID", example = "1")
        private Long storeId;
        @Schema(description = "가게 이름", example = "로슈아커피")
        private String name;
        @Schema(description = "가게 카테고리", example = "카페")
        private Category category;
        @Schema(description = "가게 이미지", example = "[ \"https://image1.jpg\"], \"https://image2.jpg\"]")
        private List<String> images;
        @Schema(description = "리뷰 평점", example = "4.42")
        private float rating;
        @Schema(description = "리뷰 수", example = "273")
        private int reviewCount;
        @Schema(description = "영업 시작 시간", example = "06:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime openTime;
        @Schema(description = "영업 종료 시간", example = "21:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime closeTime;
        @Schema(description = "영업 상태", example = "영업중")
        private BusinessStatus businessStatus;

        @Builder
        public CurationStoreInfoDto(Long storeId, String name, Category category, List<String> images, float rating, int reviewCount, LocalTime openTime, LocalTime closeTime, BusinessStatus businessStatus) {
            this.storeId = storeId;
            this.name = name;
            this.category = category;
            this.images = images;
            this.rating = rating;
            this.reviewCount = reviewCount;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.businessStatus = businessStatus;
        }


        public static CurationStoreInfoDto of(Store store, BusinessHour businessHour) {
            List<String> imageUrls = new ArrayList<>();
            imageUrls.add(store.getThumbnailImageUrl());
            imageUrls.addAll(store.getImages().stream()
                    .map(StoreImage::getImageUrl)
                    .toList());

            return CurationStoreInfoDto.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .category(store.getCategory())
                    .images(imageUrls)
                    .rating(store.getRating())
                    .reviewCount(store.getReviewCount())
                    .openTime(businessHour.getOpenTime())
                    .closeTime(businessHour.getCloseTime())
                    .businessStatus(BusinessStatus.checkBusinessStatus(businessHour.getIsHoliday(), businessHour.getOpenTime(), businessHour.getCloseTime()))
                    .build();
        }
}
