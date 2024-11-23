package com.goorm.LocC.review.repository;

import com.goorm.LocC.review.domain.Review;
import com.goorm.LocC.review.dto.ReviewInfoDto;
import com.goorm.LocC.store.domain.Store;
import com.goorm.LocC.store.dto.response.DetailStoreRespDto.SimpleReviewInfo;
import com.goorm.LocC.store.dto.condition.RegionCond;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {

    List<ReviewInfoDto> findReviewInfoDtosByProvinceAndCity(RegionCond condition);
    Optional<Review> findReviewDetailById(Long reviewId);
    List<SimpleReviewInfo> findSimpleReviewDtosByStore(Store store);
}
