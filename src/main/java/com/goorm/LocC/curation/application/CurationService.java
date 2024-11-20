package com.goorm.LocC.curation.application;

import com.goorm.LocC.curation.domain.Curation;
import com.goorm.LocC.curation.domain.CurationBookmark;
import com.goorm.LocC.curation.dto.ToggleCurationBookmarkRespDto;
import com.goorm.LocC.curation.exception.CurationException;
import com.goorm.LocC.curation.repository.CurationBookmarkRepository;
import com.goorm.LocC.curation.repository.CurationRepository;
import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.goorm.LocC.curation.exception.CurationErrorCode.CURATION_NOT_FOUND;
import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class CurationService {

    private final MemberRepository memberRepository;
    private final CurationRepository curationRepository;
    private final CurationBookmarkRepository curationBookmarkRepository;

    public ToggleCurationBookmarkRespDto toggleBookmark(Long storeId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
        Curation curation = curationRepository.findById(storeId)
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
}
