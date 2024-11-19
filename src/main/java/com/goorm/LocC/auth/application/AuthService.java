package com.goorm.LocC.auth.application;

import com.goorm.LocC.auth.dto.KakaoInfoDto;
import com.goorm.LocC.auth.dto.TokenRespDto;
import com.goorm.LocC.auth.jwt.utils.JwtUtil;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final KakaoAuthService kakaoAuthService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public TokenRespDto kakaoLogin(String kakaoAccessToken) {
        KakaoInfoDto kakaoInfo = kakaoAuthService.getKakaoInfo(kakaoAccessToken);
        Member member = memberRepository.findBySocialId(kakaoInfo.getSocialId())
                .orElseGet(() -> memberRepository.save(kakaoInfo.toEntity()));

        return jwtUtil.issueAccessToken(member.getEmail(), member.getUsername());
    }
}
