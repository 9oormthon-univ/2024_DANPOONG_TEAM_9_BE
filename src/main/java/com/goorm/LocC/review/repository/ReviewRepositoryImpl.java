package com.goorm.LocC.review.repository;

import com.goorm.LocC.review.dto.ReviewInfoDto;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.dto.RegionCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.goorm.LocC.review.domain.QReview.review;
import static com.goorm.LocC.store.domain.QStore.store;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ReviewInfoDto> findReviewsByProvinceAndCity(RegionCond condition) {

        return queryFactory
                .select(
                    Projections.constructor(ReviewInfoDto.class,
                        review.reviewId,
                            review.member.username,
                            store.name.as("storeName"),
                            store.category,
                            review.rating,
                            store.reviewCount,
                            review.imageUrl
                    )
                )
                .from(review)
                .join(review.store, store)
                .where(
                        eqProvince(condition.getProvince()),
                        eqCity(condition.getCity())
                )
                .limit(condition.getLimit())
                .orderBy(review.likeCount.desc())
                .fetch();
    }

    private BooleanExpression eqProvince(Province province) {
        return province != null ? store.province.eq(province) : null;
    }

    private BooleanExpression eqCity(City city) {
        return city != null ? store.city.eq(city) : null;
    }
}
