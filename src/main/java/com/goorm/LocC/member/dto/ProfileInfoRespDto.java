package com.goorm.LocC.member.dto;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.store.dto.StoreInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileInfoRespDto {

    @Schema(description = "닉네임", example = "미르미0303")
    private String username;
    @Schema(description = "아이디", example = "Mirmi")
    private String handle;
    @Schema(description = "프로필 이미지", example = "https://image.jpg")
    private String profileImageUrl;
    @Schema(description = "저장한 큐레이션 리스트")
    private List<CurationInfoDto> savedCurations;
    @Schema(description = "저장한 가게 리스트")
    private List<StoreInfoDto> savedStores;

    @Builder
    public ProfileInfoRespDto(String username, String handle, String profileImageUrl, List<CurationInfoDto> savedCurations, List<StoreInfoDto> savedStores) {
        this.username = username;
        this.handle = handle;
        this.profileImageUrl = profileImageUrl;
        this.savedCurations = savedCurations;
        this.savedStores = savedStores;
    }

    public static ProfileInfoRespDto of(Member member, List<CurationInfoDto> curations, List<StoreInfoDto> stores) {
        return ProfileInfoRespDto.builder()
                .username(member.getUsername())
                .handle(member.getHandle())
                .profileImageUrl(member.getProfileImageUrl())
                .savedCurations(curations)
                .savedStores(stores)
                .build();
    }
}
