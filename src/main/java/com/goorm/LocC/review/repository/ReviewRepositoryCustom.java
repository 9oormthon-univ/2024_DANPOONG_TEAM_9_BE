package com.goorm.LocC.review.repository;

import com.goorm.LocC.review.domain.Review;
import com.goorm.LocC.review.dto.ReviewInfoDto;
import com.goorm.LocC.store.dto.RegionCond;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {

    List<ReviewInfoDto> findTop5ByProvinceAndCity(RegionCond condition);

    Optional<Review> findReviewDetailById(Long reviewId); // 새로운 메서드 추가
}
