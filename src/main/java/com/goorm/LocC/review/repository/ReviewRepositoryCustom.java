package com.goorm.LocC.review.repository;

import com.goorm.LocC.review.dto.ReviewInfoDto;
import com.goorm.LocC.store.dto.RegionCond;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewInfoDto> findTop5ByProvinceAndCity(RegionCond condition);
}
