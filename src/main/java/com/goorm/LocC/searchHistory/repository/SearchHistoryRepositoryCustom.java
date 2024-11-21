package com.goorm.LocC.searchHistory.repository;

import com.goorm.LocC.searchHistory.dto.RecentKeywordCond;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto.RecentKeywordInfoDto;

import java.util.List;

public interface SearchHistoryRepositoryCustom {

    List<String> findPopularKeywordsTop10();
    List<RecentKeywordInfoDto> findRecentKeywordsByMember(RecentKeywordCond condition);
}
