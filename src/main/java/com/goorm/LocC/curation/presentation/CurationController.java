package com.goorm.LocC.curation.presentation;

import com.goorm.LocC.auth.dto.CustomUserDetails;
import com.goorm.LocC.curation.application.CurationService;
import com.goorm.LocC.curation.dto.response.DetailCurationRespDto;
import com.goorm.LocC.curation.dto.response.ToggleCurationBookmarkRespDto;
import com.goorm.LocC.global.common.dto.ApiResponse;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "큐레이션", description = "큐레이션 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/curations")
public class CurationController {

    private final CurationService curationService;

    // 북마크 토글 기능
    @Operation(summary = "큐레이션 북마크 토글", description = "큐레이션 북마크를 저장/삭제합니다.")
    @PutMapping("/{curationId}/bookmark/toggle")
    public ResponseEntity<ApiResponse<ToggleCurationBookmarkRespDto>> toggleBookmark(
            @Parameter(description = "큐레이션 ID", example = "1")
            @PathVariable Long curationId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(curationService.toggleBookmark(curationId, user.getEmail()))
        );
    }

    // 큐레이션 상세 조회 기능
    @Operation(summary = "큐레이션 상세 조회", description = "큐레이션의 상세 정보를 반환합니다.")
    @GetMapping("/{curationId}")
    public ResponseEntity<ApiResponse<DetailCurationRespDto>> getCurationDetail(
            @Parameter(description = "큐레이션 ID", example = "1")
            @PathVariable Long curationId,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(
                ApiResponse.success(curationService.getCurationDetail(curationId, user.getEmail()))
        );
    }
}
