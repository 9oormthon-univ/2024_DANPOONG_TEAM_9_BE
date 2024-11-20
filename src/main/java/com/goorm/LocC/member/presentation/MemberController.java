package com.goorm.LocC.member.presentation;

import com.goorm.LocC.auth.dto.CustomUserDetails;
import com.goorm.LocC.global.common.dto.ApiResponse;
import com.goorm.LocC.member.application.MemberService;
import com.goorm.LocC.member.dto.ProfileInfoRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/me/profile")
    public ResponseEntity<ApiResponse<ProfileInfoRespDto>> getProfile(
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(ApiResponse.success(
                memberService.getProfile(user.getEmail())
        ));
    }
}
