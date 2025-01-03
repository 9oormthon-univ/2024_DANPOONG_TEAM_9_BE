package com.goorm.LocC.searchHistory.dto.response;

import com.goorm.LocC.searchHistory.domain.SearchHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchKeywordRespDto {
    @Schema(description = "기준 시간", example = "2024-11-22T12:11:00")
    private LocalDateTime baseTime;
    private List<RecentKeywordInfoDto> recentKeywords;
    @Schema(description = "추천 검색어", example = "[\"부산 카페\", \"부산 스테이\"]")
    private List<String> recommendedKeywords;
    @Schema(description = "인기 검색어", example = "[\"성수\", \"행궁동\", \"감자빵\"]")
    private List<String> popularKeywords;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class RecentKeywordInfoDto {
        @Schema(description = "검색 기록 ID", example = "1")
        private Long searchHistoryId;
        @Schema(description = "검색어", example = "성수")
        private String keyword;

        public RecentKeywordInfoDto(Long searchHistoryId, String keyword) {
            this.searchHistoryId = searchHistoryId;
            this.keyword = keyword;
        }

        public static RecentKeywordInfoDto from(SearchHistory searchHistory) {
            return new RecentKeywordInfoDto(searchHistory.getSearchHistoryId(), searchHistory.getKeyword());
        }
    }

    @Builder
    public SearchKeywordRespDto(List<RecentKeywordInfoDto> recentKeywords, List<String> recommendedKeywords, List<String> popularKeywords) {
        this.baseTime = LocalDateTime.now();
        this.recentKeywords = recentKeywords;
        this.recommendedKeywords = recommendedKeywords;
        this.popularKeywords = popularKeywords;
    }
}
