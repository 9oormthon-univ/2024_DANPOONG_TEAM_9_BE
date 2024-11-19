package com.goorm.LocC.auth.presentation;

import com.goorm.LocC.auth.application.AuthService;
import com.goorm.LocC.auth.dto.TokenRespDto;
import com.goorm.LocC.global.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인", description = "로그인 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao")
    @Operation(summary = "카카오 회원가입 및 로그인")
    public ResponseEntity<ApiResponse<TokenRespDto>> kakaoLogin(
            @Schema(description = "카카오 액세스 토큰", example = "Bearer bVF0Z136P6bHrZwEhOKJe21sOJtb9a_vAAAAAQo8IpsAAAGTRCyz-1XuKbObXTiX")
            @RequestHeader("Kakao-Authorization") String kakaoAccessToken) {
        return ResponseEntity.ok(
                ApiResponse.success(
                authService.kakaoLogin(kakaoAccessToken)));
    }
}
