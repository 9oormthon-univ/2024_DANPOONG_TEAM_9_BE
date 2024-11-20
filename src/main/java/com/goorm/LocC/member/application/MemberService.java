package com.goorm.LocC.member.application;

import com.goorm.LocC.curation.dto.CurationInfoDto;
import com.goorm.LocC.curation.repository.CurationBookmarkRepository;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.dto.MemberCond;
import com.goorm.LocC.member.dto.ProfileInfoRespDto;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
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

    public ProfileInfoRespDto getProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        MemberCond condition = new MemberCond(member);
        List<CurationInfoDto> curations = curationBookmarkRepository.searchCurationsByMember(condition);
        List<StoreInfoDto> stores = storeBookmarkRepository.searchStoresByMember(condition);

        return ProfileInfoRespDto.of(member, curations, stores);
    }
}
