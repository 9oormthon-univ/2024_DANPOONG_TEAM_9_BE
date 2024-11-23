package com.goorm.LocC.member.application;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.curation.repository.CurationBookmarkRepository;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.domain.PreferredCategory;
import com.goorm.LocC.member.dto.condition.MemberCond;
import com.goorm.LocC.member.dto.request.PreferenceReqDto;
import com.goorm.LocC.member.dto.response.ProfileInfoRespDto;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.store.dto.SimpleStoreInfoDto;
import com.goorm.LocC.store.repository.StoreBookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CurationBookmarkRepository curationBookmarkRepository;
    private final StoreBookmarkRepository storeBookmarkRepository;

    @Transactional(readOnly = true)
    public ProfileInfoRespDto getProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        MemberCond condition = new MemberCond(member);
        List<CurationInfoDto> curations = curationBookmarkRepository.findCurationInfoDtosByMember(condition);
        List<SimpleStoreInfoDto> stores = storeBookmarkRepository.findSimpleStoreInfoDtosByMember(condition);

        return ProfileInfoRespDto.of(member, curations, stores);
    }

    @Transactional
    public void savePreferences(String email, PreferenceReqDto request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        // 관심 카테고리 설정 (최대 2개)
//        member.getPreferredCategories().clear();
//        request.getCategories().stream()
//                .limit(2)
//                .forEach(category ->
//                        member.getPreferredCategories().add(new PreferredCategory(member, Category.valueOf(category)))
//                );
        member.getPreferredCategories().clear(); // 기존 카테고리 제거
        request.getCategories().stream()
                .limit(2) // 최대 2개만 처리
                .forEach(category -> member.getPreferredCategories().add(new PreferredCategory(member, category)));



        // 선호 지역 설정 city
//        City preferredCity = City.valueOf(request.getCity());
//        Province preferredProvince = preferredCity.getProvince(); // City와 연결된 Province 가져오기
//        member.setPreferredCity(preferredCity);
//        member.setPreferredProvince(preferredProvince);
//        });

        // 선호 지역 설정 province
        member.setPreferredProvince(request.getProvince());
        memberRepository.save(member); // 변경된 데이터 저장

    }

}
