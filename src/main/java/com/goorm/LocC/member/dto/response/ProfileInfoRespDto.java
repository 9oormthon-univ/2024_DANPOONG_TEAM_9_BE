package com.goorm.LocC.member.dto.response;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.dto.SimpleStoreInfoDto;
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
    private List<SimpleStoreInfoDto> savedStores;
    @Schema(description = "선호하는 지역", example = "강원")
    private Province preferredProvince;
    @Schema(description = "선호하는 도시", example = "강릉시")
    private City preferredCity;
    @Schema(description = "선호하는 카테고리 리스트", example = "[\"카페, 맛집\"]")
    private List<Category> preferredCategories;

    @Builder
    public ProfileInfoRespDto(String username, String handle, String profileImageUrl, List<CurationInfoDto> savedCurations, List<SimpleStoreInfoDto> savedStores, Province preferredProvince, City preferredCity, List<Category> preferredCategories) {
        this.username = username;
        this.handle = handle;
        this.profileImageUrl = profileImageUrl;
        this.savedCurations = savedCurations;
        this.savedStores = savedStores;
        this.preferredProvince = preferredProvince;
        this.preferredCity = preferredCity;
        this.preferredCategories = preferredCategories;
    }

    public static ProfileInfoRespDto of(Member member, List<CurationInfoDto> curations, List<SimpleStoreInfoDto> stores) {
        return ProfileInfoRespDto.builder()
                .username(member.getUsername())
                .handle(member.getHandle())
                .profileImageUrl(member.getProfileImageUrl())
                .savedCurations(curations)
                .savedStores(stores)
                .preferredProvince(member.getPreferredProvince())
                .preferredCity(member.getPreferredCity())
                .preferredCategories(member.getPreferredCategories()
                        .stream().map(pc -> pc.getCategory()).toList())
                .build();
    }
}
