package com.goorm.LocC.review.repository;

import com.goorm.LocC.review.domain.Review;
import com.goorm.LocC.review.domain.ReviewImage;
import com.goorm.LocC.review.dto.ReviewInfoDto;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.domain.Store;
import com.goorm.LocC.store.dto.DetailStoreResp.SimpleReviewInfo;
import com.goorm.LocC.store.dto.RegionCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.goorm.LocC.member.domain.QMember.member;
import static com.goorm.LocC.review.domain.QReview.review;
import static com.goorm.LocC.review.domain.QReviewImage.reviewImage;
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

    @Override
    public Optional<Review> findReviewDetailById(Long reviewId) {
        Review foundReview = queryFactory
                .selectFrom(review)
                .join(review.store, store).fetchJoin()
                .where(review.reviewId.eq(reviewId))
                .fetchOne();

        return Optional.ofNullable(foundReview);
    }

    @Override
    public List<SimpleReviewInfo> findSimpleReviewsByStore(Store store) {
        List<Review> reviews = queryFactory
                .selectFrom(review)
                .leftJoin(review.reviewImage, reviewImage).fetchJoin()
                .join(review.member, member).fetchJoin()
                .where(review.store.eq(store))
                .limit(5)
                .fetch();

    return reviews.stream()
            .map(r -> {
                List<String> images = Stream.concat(
                        Stream.of(r.getImageUrl()), r.getReviewImage().stream().map(ReviewImage::getImageUrl))
                        .toList();

                return new SimpleReviewInfo(
                        r.getReviewId(),
                        r.getMember().getProfileImageUrl(),
                        r.getMember().getUsername(),
                        r.getRating(),
                        r.getContent().length() > 50 ? r.getContent().substring(0, 50) : r.getContent(), // 요약
                        images
                );
            })
            .collect(Collectors.toList());
    }
}
