package com.goorm.LocC.home.dto.response;

import com.goorm.LocC.advertisement.dto.AdvertisementInfoDto;
import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.home.dto.ProvinceInfoDto;
import com.goorm.LocC.review.dto.ReviewInfoDto;
import com.goorm.LocC.store.dto.BenefitStoreInfoDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HomeInfoResp {

    private List<CurationInfoDto> curations;
    private List<BenefitStoreInfoDto> benefits;
    private List<ProvinceInfoDto> provinces;
    private List<ReviewInfoDto> reviews;
    private List<AdvertisementInfoDto> advertisements;

    @Builder
    public HomeInfoResp(List<CurationInfoDto> curations, List<BenefitStoreInfoDto> benefits, List<ProvinceInfoDto> provinces, List<ReviewInfoDto> reviews, List<AdvertisementInfoDto> advertisements) {
        this.curations = curations;
        this.benefits = benefits;
        this.provinces = provinces;
        this.reviews = reviews;
        this.advertisements = advertisements;
    }
}
