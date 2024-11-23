package com.goorm.LocC.searchHistory.repository;


import com.goorm.LocC.searchHistory.dto.RecentKeywordCond;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto.RecentKeywordInfoDto;
import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.Province;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.goorm.LocC.member.domain.QMember.member;
import static com.goorm.LocC.member.domain.QPreferredCategory.preferredCategory;
import static com.goorm.LocC.searchHistory.domain.QSearchHistory.searchHistory;


@RequiredArgsConstructor
public class SearchHistoryRepositoryImpl implements SearchHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findPopularKeywordsTop10() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(14); // 최근 14일 이내의 기록만

        return queryFactory
                .select(searchHistory.keyword)
                .from(searchHistory)
                .where(searchHistory.createdAt.after(startDate))
                .groupBy(searchHistory.keyword)
                .orderBy(
                        Expressions.numberTemplate(Double.class,
                                "SUM(1 / (TIMESTAMPDIFF(HOUR, {0}, {1}) + 4))", // 검색 시간 기준으로 가중치 1/(시간차이+4)
                                Expressions.constant(now), searchHistory.createdAt
                        ).desc()
                )
                .limit(10)
                .fetch();
    }

    @Override
    public List<RecentKeywordInfoDto> findRecentKeywordsByMember(RecentKeywordCond condition) {
        return queryFactory.select(Projections.constructor(
                    RecentKeywordInfoDto.class,
                    searchHistory.searchHistoryId,
                    searchHistory.keyword
                ))
                .from(searchHistory)
                .where(searchHistory.member.eq(condition.getMember()),
                        searchHistory.isDeleted.eq(false))
                .orderBy(searchHistory.createdAt.desc())
                .limit(condition.getLimit())
                .fetch();
    }

    public List<String> getRecommendedKeyword(RecommendedKeywordCond condition) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(14); // 최근 14일 이내의 기록만

        return queryFactory
                .select(searchHistory.keyword)
                .from(searchHistory)
                .join(searchHistory.member, member)
                .leftJoin(member.preferredCategories, preferredCategory)
                .where(
                        searchHistory.createdAt.after(startDate),
                        eqPreferredProvince(condition.getProvince()), // 관심 지역이 동일한 경우
                        eqPreferredCategory(condition.getCategories())  // 관심 카테고리가 동일한 경우
                )
                .groupBy(searchHistory.keyword)
                .orderBy(
                        Expressions.numberTemplate(Double.class,
                                "SUM(1 / (TIMESTAMPDIFF(HOUR, {0}, {1}) + 4))", // 검색 시간 기준으로 가중치 1/(시간차이+4)
                                Expressions.constant(now), searchHistory.createdAt
                        ).desc()
                )
                .limit(condition.getLimit())
                .fetch();
    }

    private BooleanExpression eqPreferredCategory(List<Category> categories) {
        return (categories != null && !categories.isEmpty())
                ? preferredCategory.category.in(categories)
                : null;
    }

    BooleanExpression eqPreferredProvince(Province province) {
        return province != null ? searchHistory.member.preferredProvince.eq(province) : null;
    }
}
