package com.goorm.LocC.store.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.searchHistory.repository.SearchHistoryRepository;
import com.goorm.LocC.store.domain.*;
import com.goorm.LocC.store.dto.StoreInfoDto;
import com.goorm.LocC.store.dto.ToggleStoreBookmarkRespDto;
import com.goorm.LocC.store.exception.StoreException;
import com.goorm.LocC.store.repository.StoreBookmarkRepository;
import com.goorm.LocC.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 가게 리스트 조회 기능
    public List<StoreInfoDto> findStores(Category category, Province province, City city, String storeName, String sortBy) {
        // Repository 호출하여 데이터 조회
        List<Store> stores = storeRepository.findStoresByFilters(category, province, city, storeName, Sort.by(sortBy));

        // 결과를 StoreInfoDto로 변환하여 반환
        return stores.stream()
                .map(store -> new StoreInfoDto(
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
                        store.getIsHoliday()
                ))
                .collect(Collectors.toList());
    }


    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}
