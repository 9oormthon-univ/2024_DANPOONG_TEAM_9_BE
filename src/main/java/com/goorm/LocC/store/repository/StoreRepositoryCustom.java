package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.dto.BenefitStoreInfoDto;
import com.goorm.LocC.store.dto.RegionCond;

import java.util.List;

public interface StoreRepositoryCustom {

    List<BenefitStoreInfoDto> findBenefitStoresByProvinceAndCity(RegionCond condition);
}
