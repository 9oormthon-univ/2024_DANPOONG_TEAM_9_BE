package com.goorm.LocC.store.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.searchHistory.domain.SearchHistory;
import com.goorm.LocC.searchHistory.repository.SearchHistoryRepository;
import com.goorm.LocC.store.domain.Store;
import com.goorm.LocC.store.domain.StoreBookmark;
import com.goorm.LocC.store.dto.ToggleStoreBookmarkRespDto;
import com.goorm.LocC.store.exception.StoreException;
import com.goorm.LocC.store.repository.StoreBookmarkRepository;
import com.goorm.LocC.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.goorm.LocC.store.exception.StoreErrorCode.STORE_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final StoreBookmarkRepository storeBookmarkRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    public ToggleStoreBookmarkRespDto toggleBookmark(Long storeId, String email) {
        Member member = findMemberByEmail(email);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(STORE_NOT_FOUND));
        Optional<StoreBookmark> optionalBookmark = storeBookmarkRepository.findStoreBookmarkByMemberAndStore(member, store);

        if (optionalBookmark.isPresent()) { // 이미 저장한 경우
            storeBookmarkRepository.delete(optionalBookmark.get());

            return ToggleStoreBookmarkRespDto.builder()
                    .count(store.subDibsCount())
                    .isBookmarked(false)
                    .build();
        } else { // 저장하지 않은 경우
            storeBookmarkRepository.save(
                    StoreBookmark.builder()
                    .member(member)
                    .store(store)
                    .build());

            return ToggleStoreBookmarkRespDto.builder()
                    .count(store.addDibsCount())
                    .isBookmarked(true)
                    .build();
        }
    }

    /**
     * 검색 기록이 6개 이상일 시 오래된 검색 기록 삭제 (삭제 여부 필드 true로 변경)
     */
    public void deleteOldSearchHistory(Member member) {
        searchHistoryRepository.findAllByMemberAndIsDeletedOrderByCreatedAtDesc(member, false)
                .stream()
                .skip(5)
                .forEach(SearchHistory::delete);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}
