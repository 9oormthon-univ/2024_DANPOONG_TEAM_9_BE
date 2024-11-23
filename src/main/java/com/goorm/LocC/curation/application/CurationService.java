package com.goorm.LocC.curation.application;

import com.goorm.LocC.curation.domain.Curation;
import com.goorm.LocC.curation.domain.CurationBookmark;
import com.goorm.LocC.curation.dto.CurationDetailDto;
import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.store.dto.CurationStoreInfoDto;
import com.goorm.LocC.curation.dto.ToggleCurationBookmarkRespDto;
import com.goorm.LocC.curation.exception.CurationException;
import com.goorm.LocC.curation.repository.CurationBookmarkRepository;
import com.goorm.LocC.curation.repository.CurationRepository;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.store.domain.BusinessHour;
import com.goorm.LocC.store.domain.Store;
import com.goorm.LocC.store.dto.StoreDetailDto;
import com.goorm.LocC.store.repository.BusinessHourRepository;
import com.goorm.LocC.store.repository.StoreBookmarkRepository;
import com.goorm.LocC.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.goorm.LocC.curation.exception.CurationErrorCode.CURATION_NOT_FOUND;
import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class CurationService {

    private final MemberRepository memberRepository;
    private final CurationRepository curationRepository;
    private final CurationBookmarkRepository curationBookmarkRepository;
    private final StoreRepository storeRepository; // 스토어 관련 데이터 조회를 위한 Repository 추가
    private final BusinessHourRepository businessHourRepository;
    private final StoreBookmarkRepository storeBookmarkRepository;

    // 북마크 토글 기능
    public ToggleCurationBookmarkRespDto toggleBookmark(Long curationId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
        Curation curation = curationRepository.findById(curationId)
                .orElseThrow(() -> new CurationException(CURATION_NOT_FOUND));
        Optional<CurationBookmark> optionalBookmark =
                curationBookmarkRepository.findCurationBookmarkByMemberAndCuration(member, curation);

        if (optionalBookmark.isPresent()) { // 이미 저장한 경우
            curationBookmarkRepository.delete(optionalBookmark.get());

            return new ToggleCurationBookmarkRespDto(false);
        } else { // 저장하지 않은 경우
            curationBookmarkRepository.save(
                    CurationBookmark.builder()
                            .member(member)
                            .curation(curation)
                            .build());

            return new ToggleCurationBookmarkRespDto(true);
        }
    }


    // 큐레이션 상세 조회 기능
    @Transactional
    public CurationDetailDto getCurationDetail(Long curationId, String email) {
        // 큐레이션 기본 정보 조회
        Curation curation = curationRepository.findById(curationId)
                .orElseThrow(() -> new CurationException(CURATION_NOT_FOUND));
        CurationInfoDto curationInfoDto = CurationInfoDto.from(curation);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        // 큐레이션 북마크 여부 조회
        boolean isCurationBookmarked = curationBookmarkRepository.existsByMemberAndCuration(member, curation);

        // 큐레이션에 포함된 스토어 정보 조회
        List<Store> stores = storeRepository.findByCuration_CurationId(curationId);

        DayOfWeek now = LocalDate.now().getDayOfWeek();
        List<BusinessHour> businessHours = businessHourRepository.findBusinessHourByStoreInAndDayOfWeek(stores, now);

        // 가게, 영업 시간 매핑
        Map<Store, BusinessHour> storeBusinessHourMap = businessHours.stream()
                .collect(Collectors.toMap(
                        BusinessHour::getStore, // Store를 키로 사용
                        bh -> bh // BusinessHour를 값으로 사용
                ));

        stores.forEach(store -> {
            storeBookmarkRepository.findStoreBookmarkByMemberAndStore(member, store);
        });

        List<StoreDetailDto> storeDetails = storeBusinessHourMap.entrySet().stream()
                .map(
                    entry -> StoreDetailDto.builder()
                            .storeInfo(CurationStoreInfoDto.of(entry.getKey(), entry.getValue()))
                            .isBookmarked(storeBookmarkRepository.existsByMemberAndStore(member, entry.getKey()))
                            .summary(entry.getKey().getCurationCoutent())
                            .build()
                )
                .toList();

        // 응답 DTO 생성
        return CurationDetailDto.builder()
                .isBookmarked(isCurationBookmarked)
                .curationInfo(curationInfoDto)
                .stores(storeDetails)
                .build();
    }

}
