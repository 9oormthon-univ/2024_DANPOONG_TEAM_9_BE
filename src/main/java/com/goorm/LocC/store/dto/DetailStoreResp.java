package com.goorm.LocC.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.LocC.store.domain.BusinessHour;
import com.goorm.LocC.store.domain.BusinessStatus;
import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DetailStoreResp {

    @Schema(description = "가게 이름", example = "로슈아커피")
    private String storeName;
    @Schema(description = "북마크 여부", example = "true")
    private boolean isBookmarked;
    @Schema(description = "카테고리", example = "카페")
    private Category category;
    @Schema(description = "가게 주소", example = "서울시 강남구 역삼동 123-45")
    private String address;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "가게 이미지", example = "https://image1.jpg")
    private String imageUrl;
    @Schema(description = "영업 시작 시간", example = "06:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime openTime;
    @Schema(description = "영업 종료 시간", example = "21:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closeTime;
    @Schema(description = "영업 상태", example = "영업중")
    private BusinessStatus status;
    @Schema(description = "홈페이지", example = "https://locc.com")
    private String homepage;
    @Schema(description = "리뷰 평점", example = "4.42")
    private float rating;
    @Schema(description = "리뷰 수", example = "273")
    private int reviewCount;
    @Schema(description = "가게 소개", example = "즉석 열풍 로스팅으로 특별한 건어물을 제공합니다.")
    private String content;
    private List<BusinessHourInfo> businessHours;
    private List<SimpleReviewInfo> reviews;
    private List<NearStoreInfoDto> nearbyStores;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class BusinessHourInfo {
        @Schema(description = "요일", example = "MONDAY")
        private DayOfWeek dayOfWeek;
        @Schema(description = "영업 시작 시간", example = "06:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime openTime;
        @Schema(description = "영업 종료 시간", example = "21:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime closeTime;
        @Schema(description = "휴무 여부", example = "false")
        private boolean isHoliday;

        public BusinessHourInfo(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, boolean isHoliday) {
            this.dayOfWeek = dayOfWeek;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.isHoliday = isHoliday;
        }

        public static BusinessHourInfo from(BusinessHour businessHour) {
            return new BusinessHourInfo(businessHour.getDayOfWeek(), businessHour.getOpenTime(), businessHour.getCloseTime(), businessHour.getIsHoliday());
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SimpleReviewInfo {
        @Schema(description = "리뷰 ID", example = "1")
        private Long reviewId;
        @Schema(description = "유저 닉네임", example = "미르미")
        private String username;
        @Schema(description = "평점", example = "4")
        private int rating;
        @Schema(description = "내용 요약", example = "직원이 친절하심. 사기 전에 시식할 수 있고 제품별로 특징과 고객의 원하는 입맛을 고려해서")
        private String summary;
        @Schema(description = "리뷰 이미지", example = "[\"https://image1.jpg\", \"https://image2.jpg\"]")
        private List<String> images;

        public SimpleReviewInfo(Long reviewId, String username, int rating, String summary, List<String> images) {
            this.reviewId = reviewId;
            this.username = username;
            this.rating = rating;
            this.summary = summary;
            this.images = images;
        }
    }

    @Builder
    public DetailStoreResp(String storeName, boolean isBookmarked, String address, Category category, String phone, String imageUrl, LocalTime openTime, LocalTime closeTime, BusinessStatus status, String homepage, float rating, int reviewCount, String content, List<BusinessHourInfo> businessHours, List<SimpleReviewInfo> reviews, List<NearStoreInfoDto> nearbyStores) {
        this.storeName = storeName;
        this.isBookmarked = isBookmarked;
        this.category = category;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.status = status;
        this.homepage = homepage;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.content = content;
        this.businessHours = businessHours;
        this.reviews = reviews;
        this.nearbyStores = nearbyStores;
    }

    public static DetailStoreResp of(Store store, boolean isBookmarked, List<BusinessHourInfo> businessHours, List<SimpleReviewInfo> reviews, List<NearStoreInfoDto> nearbyStores) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        Optional<BusinessHourInfo> today = businessHours.stream().filter(
                        businessHourInfo -> businessHourInfo.getDayOfWeek().equals(dayOfWeek))
                .findFirst();

        BusinessStatus businessStatus = today.map(bh -> BusinessStatus.checkBusinessStatus(bh.isHoliday(), bh.getOpenTime(), bh.getCloseTime()))
                .orElse(BusinessStatus.HOLIDAY);
        LocalTime openTime = today.map(BusinessHourInfo::getOpenTime)
                .orElse(null);
        LocalTime closeTime = today.map(BusinessHourInfo::getCloseTime)
                .orElse(null);

        return DetailStoreResp.builder()
                .storeName(store.getName())
                .imageUrl(store.getThumbnailImageUrl())
                .isBookmarked(isBookmarked)
                .category(store.getCategory())
                .phone(store.getPhone())
                .openTime(openTime)
                .closeTime(closeTime)
                .address(store.getAddress())
                .status(businessStatus)
                .homepage(store.getHomepageUrl())
                .rating(store.getRating())
                .reviewCount(store.getReviewCount())
                .content(store.getContent())
                .businessHours(businessHours)
                .reviews(reviews)
                .nearbyStores(nearbyStores)
                .build();
    }
}
