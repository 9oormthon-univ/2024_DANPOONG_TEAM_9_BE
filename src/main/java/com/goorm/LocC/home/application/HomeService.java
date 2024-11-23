package com.goorm.LocC.home.application;

import com.goorm.LocC.advertisement.dto.AdvertisementInfoDto;
import com.goorm.LocC.advertisement.repository.AdvertisementRepository;
import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.curation.repository.CurationRepository;
import com.goorm.LocC.home.dto.response.HomeInfoResp;
import com.goorm.LocC.home.dto.ProvinceInfoDto;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.review.dto.ReviewInfoDto;
import com.goorm.LocC.review.repository.ReviewRepository;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.dto.BenefitStoreInfoDto;
import com.goorm.LocC.store.dto.condition.RegionCond;
import com.goorm.LocC.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Transactional
@RequiredArgsConstructor
@Service
public class HomeService {

    private final MemberRepository memberRepository;
    private final CurationRepository curationRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final AdvertisementRepository advertisementRepository;

    @Transactional(readOnly = true)
    public HomeInfoResp getHomeInfo(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        Province preferredProvince = member.getPreferredProvince();
        City preferredCity = member.getPreferredCity();
        RegionCond regionCond = new RegionCond(preferredProvince, preferredCity, 5);

        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        YearMonth now = YearMonth.now();
        List<CurationInfoDto> curations = curationRepository.findCurationByPublishedDate(firstDayOfMonth)
                .stream()
                .map(CurationInfoDto::from)
                .toList();

        List<BenefitStoreInfoDto> benefits = storeRepository.findBenefitStoreInfoDtosByProvinceAndCity(regionCond);

        List<ProvinceInfoDto> provinces = Arrays.stream(Province.values())
                .map(ProvinceInfoDto::of)
                .toList();

        List<ReviewInfoDto> reviews = reviewRepository.findReviewInfoDtosByProvinceAndCity(regionCond);

        List<AdvertisementInfoDto> advertisements = advertisementRepository.findAll().stream()
                .map(AdvertisementInfoDto::from)
                .toList();

        return HomeInfoResp.builder()
                .curations(curations)
                .benefits(benefits)
                .provinces(provinces)
                .reviews(reviews)
                .advertisements(advertisements)
                .build();
    }
}
