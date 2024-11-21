package com.goorm.LocC.searchHistory.repository;


import com.goorm.LocC.searchHistory.dto.RecentKeywordCond;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto.RecentKeywordInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.goorm.LocC.searchHistory.domain.QSearchHistory.searchHistory;


@RequiredArgsConstructor
public class SearchHistoryRepositoryImpl implements SearchHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findPopularKeywordsTop10() {
        LocalDateTime startDate = LocalDateTime.now().minusHours(24);

        return queryFactory.select(searchHistory.keyword)
                .from(searchHistory)
                .where(searchHistory.createdAt.after(startDate))
                .groupBy(searchHistory.keyword)
                .orderBy(searchHistory.keyword.count().desc())
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
                .where(searchHistory.member.eq(condition.getMember()))
                .orderBy(searchHistory.createdAt.desc())
                .limit(condition.getLimit())
                .fetch();
    }
}
