package com.goorm.LocC.curation.application;

import com.goorm.LocC.curation.domain.Curation;
import com.goorm.LocC.curation.domain.CurationBookmark;
import com.goorm.LocC.curation.dto.CurationDetailDto;
import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.store.dto.StoreDetailDto;

import com.goorm.LocC.curation.dto.ToggleCurationBookmarkRespDto;
import com.goorm.LocC.curation.exception.CurationException;
import com.goorm.LocC.curation.repository.CurationBookmarkRepository;
import com.goorm.LocC.curation.repository.CurationRepository;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.store.domain.Store;
import com.goorm.LocC.store.dto.StoreInfoDto;
import com.goorm.LocC.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public CurationDetailDto getCurationDetail(Long curationId) {
        // 큐레이션 기본 정보 조회
        Curation curation = curationRepository.findById(curationId)
                .orElseThrow(() -> new CurationException(CURATION_NOT_FOUND));
        CurationInfoDto curationInfoDto = CurationInfoDto.from(curation);

        // 큐레이션에 포함된 스토어 정보 조회
        List<StoreDetailDto> stores = storeRepository.findByCuration_CurationId(curationId)
                .stream()
                .map(store -> StoreDetailDto.builder()
                        .storeInfo(new StoreInfoDto(
                                store.getStoreId(),
                                store.getName(),
                                store.getCategory(),
                                store.getProvince(),
                                store.getCity(),
                                store.getThumbnailImageUrl(),
                                store.getRating(),
                                store.getReviewCount(),
                                store.getOpenTime(),
                                store.getCloseTime(),
                                false // isHoliday 기본값
                        ))
                        .isBookmarked(false)                  // 북마크 여부 기본값 설정
                        .distance(0.0f)                       // 거리 기본값 설정
                        .summary(store.getContent())          // Store의 요약 정보
                        .build())
                .collect(Collectors.toList());

        // 응답 DTO 생성
        return CurationDetailDto.builder()
                .curationInfo(curationInfoDto)
                .stores(stores)
                .build();
    }

}
