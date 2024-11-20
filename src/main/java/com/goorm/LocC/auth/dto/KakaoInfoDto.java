package com.goorm.LocC.auth.dto;

import com.goorm.LocC.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.goorm.LocC.member.domain.SocialType.KAKAO;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KakaoInfoDto {

    private String socialId;
    private String nickname;
    private String email;
    private String profileImageUrl;

    @Builder
    public KakaoInfoDto(String socialId, String nickname, String email, String profileImageUrl) {
        this.socialId = socialId;
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    public Member toEntity() {
        return Member.builder()
                .socialType(KAKAO)
                .socialId(socialId)
                .username(nickname)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
