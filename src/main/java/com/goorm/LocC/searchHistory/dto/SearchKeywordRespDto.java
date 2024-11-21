package com.goorm.LocC.searchHistory.dto;

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
    private LocalDateTime baseTime;
    private List<RecentKeywordInfoDto> recentKeywords;
    private List<String> recommendedKeywords;
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
    }

    @Builder
    public SearchKeywordRespDto(List<RecentKeywordInfoDto> recentKeywords, List<String> recommendedKeywords, List<String> popularKeywords) {
        this.baseTime = LocalDateTime.now();
        this.recentKeywords = recentKeywords;
        this.recommendedKeywords = recommendedKeywords;
        this.popularKeywords = popularKeywords;
    }
}
