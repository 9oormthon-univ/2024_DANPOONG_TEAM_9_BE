package com.goorm.LocC.review.presentation;

import com.goorm.LocC.auth.dto.CustomUserDetails;
import com.goorm.LocC.global.common.dto.ApiResponse;
import com.goorm.LocC.review.application.ReviewService;
import com.goorm.LocC.review.dto.response.DetailReviewRespDto;
import com.goorm.LocC.store.dto.response.ToggleStoreBookmarkRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰", description = "리뷰 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 좋아요 토글", description = "리뷰 좋아요를 저장/삭제합니다.")
    @PutMapping("/{reviewId}/bookmark/toggle")
    public ResponseEntity<ApiResponse<ToggleStoreBookmarkRespDto>> toggleBookmark(
            @Parameter(description = "리뷰 ID", example = "1")
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(reviewService.toggleLike(reviewId, user.getEmail()))
        );
    }

    // 개별 리뷰 상세 조회 API
    @Operation(summary = "개별 리뷰 상세 조회", description = "reviewId로 리뷰 상세 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<DetailReviewRespDto>> getReviewDetail(
            @Parameter(description = "리뷰 ID", example = "1")
            @RequestParam Long storeId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        DetailReviewRespDto reviewDetail = reviewService.getReviewDetail(storeId, user.getEmail());
        return ResponseEntity.ok(ApiResponse.success(reviewDetail));
    }
}
