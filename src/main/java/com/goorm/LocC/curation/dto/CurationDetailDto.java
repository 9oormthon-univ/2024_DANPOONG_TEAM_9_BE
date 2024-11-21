package com.goorm.LocC.curation.dto;

import com.goorm.LocC.store.dto.StoreDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CurationDetailDto {

    @Schema(description = "큐레이션 기본 정보")
    private CurationInfoDto curationInfo;

    @Schema(description = "큐레이션에 포함된 스토어 리스트")
    private List<StoreDetailDto> stores;

    @Builder
    public CurationDetailDto(CurationInfoDto curationInfo, List<StoreDetailDto> stores) {
        this.curationInfo = curationInfo;
        this.stores = stores;
    }
}
