package com.goorm.LocC.curation.dto.response;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.curation.dto.CurationStoreInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DetailCurationRespDto {

    @Schema(description = "큐레이션 기본 정보")
    private CurationInfoDto curationInfo;

    @Schema(description = "큐레이션에 포함된 스토어 리스트")
    private List<CurationStoreInfoDto> stores;

    @Schema(description = "해당 유저의 큐레이션 북마크 여부")
    private boolean isBookmarked;

    @Builder
    public DetailCurationRespDto(CurationInfoDto curationInfo, List<CurationStoreInfoDto> stores, boolean isBookmarked) {
        this.curationInfo = curationInfo;
        this.stores = stores;
        this.isBookmarked = isBookmarked;
    }
}
