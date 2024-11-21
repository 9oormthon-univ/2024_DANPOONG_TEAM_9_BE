package com.goorm.LocC.curation.dto;

import com.goorm.LocC.curation.domain.Curation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CurationInfoDto {

    @Schema(description = "큐레이션 ID", example = "1")
    private Long curationId;
    @Schema(description = "큐레이션 타이틀", example = "낙엽도 맛집이 있나요 TOP5")
    private String title;
    @Schema(description = "큐레이션 서브 타이틀", example = "낙엽이 떨어지기 직전인 지금 가봐야 할 서울 근교 낙엽 맛집")
    private String subtitle;
    @Schema(description = "큐레이션 이미지", example = "https://image.jpg")
    private String imageUrl;

    @Builder
    public CurationInfoDto(Long curationId, String title, String subtitle, String imageUrl) {
        this.curationId = curationId;
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }

    public static CurationInfoDto from(Curation curation) {
        return CurationInfoDto.builder()
                .curationId(curation.getCurationId())
                .title(curation.getTitle())
                .subtitle(curation.getSubtitle())
                .imageUrl(curation.getImageUrl())
                .build();
    }
}
