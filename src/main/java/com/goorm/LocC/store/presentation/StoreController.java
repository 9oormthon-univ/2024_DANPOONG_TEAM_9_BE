package com.goorm.LocC.store.presentation;

import com.goorm.LocC.auth.dto.CustomUserDetails;
import com.goorm.LocC.global.common.dto.ApiResponse;
import com.goorm.LocC.store.application.StoreService;
import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.dto.StoreInfoDto;
import com.goorm.LocC.store.dto.ToggleStoreBookmarkRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "가게 리스트 조회", description = "카테고리, 지역, 검색어 등으로 가게 리스트를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StoreInfoDto>>> getStores(
            @Parameter(description = "카테고리") @RequestParam(required = false) List<Category> category,
            @Parameter(description = "도/광역시") @RequestParam(required = false) Province province,
            @Parameter(description = "시/군/구") @RequestParam(required = false) City city,
            @Parameter(description = "가게 이름") @RequestParam(required = false) String storeName,
            @Parameter(description = "정렬 기준", example = "name") @RequestParam(defaultValue = "name") String sortBy
    ) {
        List<StoreInfoDto> stores = storeService.findStores(category, province, city, storeName, sortBy);
        return ResponseEntity.ok(ApiResponse.success(stores));
    }

    @Operation(summary = "가게 상세 조회", description = "가게 ID로 가게 상세 정보를 조회합니다.")
    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<Void>> getStoreById(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable Long storeId
    ) {
        storeService.findById(storeId);
//        return ResponseEntity.ok(ApiResponse.success());
        return null;
    }
}
