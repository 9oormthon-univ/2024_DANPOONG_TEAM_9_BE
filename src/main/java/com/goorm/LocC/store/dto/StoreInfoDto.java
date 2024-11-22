    package com.goorm.LocC.store.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import com.fasterxml.jackson.annotation.JsonInclude;
    import com.goorm.LocC.store.domain.BusinessStatus;
    import com.goorm.LocC.store.domain.Category;
    import com.goorm.LocC.store.domain.City;
    import com.goorm.LocC.store.domain.Province;
    import io.swagger.v3.oas.annotations.media.Schema;
    import lombok.AccessLevel;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.LocalTime;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public class StoreInfoDto {

        @Schema(description = "가게 ID", example = "1")
        private Long storeId;
        @Schema(description = "가게 이름", example = "로슈아커피")
        private String name;
        @Schema(description = "가게 카테고리", example = "카페")
        private Category category;
        @Schema(description = "가게 특별시/광역시/도", example = "경기")
        private Province province;
        @Schema(description = "가게 시/군/구", example = "양주시")
        private City city;
        @Schema(description = "가게 이미지", example = "https://image.jpg")
        private String imageUrl;
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

        public StoreInfoDto(Long storeId, String name, Category category, Province province, City city, String imageUrl, float rating, int reviewCount, LocalTime openTime, LocalTime closeTime, Boolean isHoliday) {
            this.storeId = storeId;
            this.name = name;
            this.category = category;
            this.province = province;
            this.city = city;
            this.imageUrl = imageUrl;
            this.rating = Math.round(rating * 100) / 100.0f; // 소수점 둘째자리까지
            this.reviewCount = reviewCount;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.businessStatus = BusinessStatus.checkBusinessStatus(isHoliday, openTime, closeTime);
        }
    }
