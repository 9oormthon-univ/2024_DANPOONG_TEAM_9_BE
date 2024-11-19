package com.goorm.LocC.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenRespDto {

    @Schema(description = "닉네임", example = "구름")
    private String username;
    @Schema(description = "이메일", example = "test@test.ac.kr")
    private String email;
    @Schema(description = "jwt access 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdW")
    private String accessToken;

    @Builder

    public TokenRespDto(String username, String email, String accessToken) {
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
    }
}

