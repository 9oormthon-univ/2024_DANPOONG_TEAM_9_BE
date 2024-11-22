package com.goorm.LocC.member.application;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.curation.repository.CurationBookmarkRepository;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.domain.PreferredCategory;
import com.goorm.LocC.member.dto.MemberCond;
import com.goorm.LocC.member.dto.PreferenceRequest;
import com.goorm.LocC.member.dto.ProfileInfoRespDto;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.dto.StoreInfoDto;
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
        List<CurationInfoDto> curations = curationBookmarkRepository.searchCurationsByMember(condition);
        List<StoreInfoDto> stores = storeBookmarkRepository.searchStoresByMember(condition);

        return ProfileInfoRespDto.of(member, curations, stores);
    }

    @Transactional
    public void savePreferences(String email, PreferenceRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        // 관심 카테고리 설정 (최대 2개)
        member.getPreferredCategories().clear();
        request.getCategories().stream()
                .limit(2)
                .forEach(category ->
                        member.getPreferredCategories().add(new PreferredCategory(member, Category.valueOf(category)))
                );

        // 선호 지역 설정 city
//        City preferredCity = City.valueOf(request.getCity());
//        Province preferredProvince = preferredCity.getProvince(); // City와 연결된 Province 가져오기
//        member.setPreferredCity(preferredCity);
//        member.setPreferredProvince(preferredProvince);
//        });
        // 선호 지역 설정 province

        Province preferredProvince = Province.valueOf(request.getProvince());
        member.setPreferredProvince(preferredProvince);

        memberRepository.save(member); // 변경된 데이터 저장

    }

}
