package com.goorm.LocC.searchHistory.presentation;

import com.goorm.LocC.auth.dto.CustomUserDetails;
import com.goorm.LocC.global.common.dto.ApiResponse;
import com.goorm.LocC.searchHistory.application.SearchHistoryService;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "검색 기록", description = "검색 기록 조회/삭제 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/searches")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    @Operation(summary = "최근/추천/인기 검색어 조회", description = "최근/추천/인기 검색어를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<SearchKeywordRespDto>> getSearchKeywordInfo(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(searchHistoryService.getSearchKeywordInfo(user.getEmail()))
        );
    }

    @Operation(summary = "검색 기록 단일 삭제", description = "유저의 검색 기록을 삭제합니다.")
    @DeleteMapping("/{searchHistoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteSearchHistory(
            @Parameter(description = "검색 기록 ID", example = "1")
            @PathVariable Long searchHistoryId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        searchHistoryService.deleteBySearchHistoryId(user.getEmail(), searchHistoryId);
        return ResponseEntity.ok(ApiResponse.success("검색 기록 삭제에 성공하였습니다."));
    }

    @Operation(summary = "검색 기록 전체 삭제", description = "유저의 검색 기록을 전체 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<ApiResponse<String >> deleteSearchHistory(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        searchHistoryService.deleteAll(user.getEmail());
        return ResponseEntity.ok(ApiResponse.success("검색 기록 전체 삭제에 성공하였습니다."));
    }
}
