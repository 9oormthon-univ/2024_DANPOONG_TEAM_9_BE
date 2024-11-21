package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.dto.BenefitStoreInfoDto;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.dto.RegionCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.goorm.LocC.store.domain.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<BenefitStoreInfoDto> findTop5ByProvinceAndCity(RegionCond condition) {

        return queryFactory
                .select(
                    Projections.constructor(BenefitStoreInfoDto.class,
                        store.storeId,
                        store.name,
                        store.reviewCount,
                        store.province,
                        store.city,
                        store.thumbnailImageUrl.as("imageUrl")
                    )
                )
                .from(store)
                .where(
                        eqProvince(condition.getProvince()),
                        eqCity(condition.getCity())
                )
                .limit(5) // 5개만 조회
                .fetch();
    }

    private BooleanExpression eqProvince(Province province) {
        return province != null ? store.province.eq(province) : null;
    }

    private BooleanExpression eqCity(City city) {
        return city != null ? store.city.eq(city) : null;
    }
}