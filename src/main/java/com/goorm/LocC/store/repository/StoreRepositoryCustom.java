package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.domain.Store;
import com.goorm.LocC.store.dto.BenefitStoreInfoDto;
import com.goorm.LocC.store.dto.condition.NearStoreCond;
import com.goorm.LocC.store.dto.condition.RegionCond;

import java.util.List;

public interface StoreRepositoryCustom {

    List<BenefitStoreInfoDto> findBenefitStoreInfoDtosByProvinceAndCity(RegionCond condition);
    List<Store> findNearStoresByProvince(NearStoreCond condition);
}
