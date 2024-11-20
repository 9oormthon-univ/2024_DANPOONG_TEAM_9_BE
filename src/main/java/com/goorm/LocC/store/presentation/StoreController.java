package com.goorm.LocC.store.presentation;

import com.goorm.LocC.auth.dto.CustomUserDetails;
import com.goorm.LocC.global.common.dto.ApiResponse;
import com.goorm.LocC.store.application.StoreService;
import com.goorm.LocC.store.dto.ToggleStoreBookmarkRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "가게", description = "가게 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "가게 북마크 토글", description = "가게 북마크를 저장/삭제합니다.")
    @PutMapping("/{storeId}/bookmark/toggle")
    public ResponseEntity<ApiResponse<ToggleStoreBookmarkRespDto>> toggleBookmark(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable Long storeId,
            @AuthenticationPrincipal CustomUserDetails user
            ) {

        return ResponseEntity.ok(
                ApiResponse.success(storeService.toggleBookmark(storeId, user.getEmail()))
        );
    }
}
