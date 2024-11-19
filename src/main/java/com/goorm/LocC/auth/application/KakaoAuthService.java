package com.goorm.LocC.auth.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.LocC.auth.dto.KakaoInfoDto;
import com.goorm.LocC.auth.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.goorm.LocC.auth.exception.AuthErrorCode.*;
import static com.goorm.LocC.auth.utils.KakaoProperties.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoAuthService {

    public KakaoInfoDto getKakaoInfo(String socialAccessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add(AUTHORIZATION_HEADER,  socialAccessToken);

            HttpEntity<JsonNode> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<String> responseData = restTemplate
                    .postForEntity(USER_INFO_URL, httpEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseData.getBody());

            String id = jsonNode.get(ID).asText();
            JsonNode kakaoAccountNode = jsonNode.get(ACCOUNT);
            String nickname = kakaoAccountNode.get(PROFILE).get(NICKNAME).asText();
            String email = kakaoAccountNode.get(EMAIL).asText();
            String profileImageUrl = Optional.ofNullable(
                    kakaoAccountNode
                            .get(PROFILE)
                            .get(PROFILE_IMAGE_URL))
                    .map(JsonNode::asText)
                    .orElse(
                        // TODO: 프로필 이미지가 없을 경우 기본 이미지 URL 리턴
                            ""
                    );

            return KakaoInfoDto.builder()
                    .socialId(id)
                    .email(email)
                    .nickname(nickname)
                    .profileImageUrl(profileImageUrl)
                    .build();
        } catch (HttpClientErrorException e) {
            log.warn("Invalid Kakao Token: {}", e.getMessage());
            throw new AuthException(KAKAO_INVALID_TOKEN);
        } catch (Exception e) {
            log.warn("Unexpected Kakao API Error: {}", e.getMessage());
            throw new AuthException(KAKAO_API_ERROR);
        }
    }
}
