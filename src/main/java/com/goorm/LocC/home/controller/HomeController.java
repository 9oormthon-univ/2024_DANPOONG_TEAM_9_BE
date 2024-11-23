package com.goorm.LocC.home.controller;

import com.goorm.LocC.auth.dto.CustomUserDetails;
import com.goorm.LocC.global.common.dto.ApiResponse;
import com.goorm.LocC.home.application.HomeService;
import com.goorm.LocC.home.dto.response.HomeInfoResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메인 홈", description = "메인 홈 데이터 조회 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "메인 홈 데이터 조회", description = "메인 홈에 출력되는 데이터들을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<HomeInfoResp>> getHomeInfo(
            @AuthenticationPrincipal CustomUserDetails user
            ) {
        return ResponseEntity.ok(ApiResponse.success(
                        homeService.getHomeInfo(user.getEmail())));
    }
}
