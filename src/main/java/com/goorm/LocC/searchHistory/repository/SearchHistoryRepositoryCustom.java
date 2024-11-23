package com.goorm.LocC.searchHistory.repository;

import com.goorm.LocC.searchHistory.dto.condition.RecentKeywordCond;
import com.goorm.LocC.searchHistory.dto.response.SearchKeywordRespDto.RecentKeywordInfoDto;

import java.util.List;

public interface SearchHistoryRepositoryCustom {

    List<String> findPopularKeywordsTop10();
    List<RecentKeywordInfoDto> findRecentKeywordInfoDtoByMember(RecentKeywordCond condition);
    List<String> getRecommendedKeywords(RecommendedKeywordCond condition);
}
