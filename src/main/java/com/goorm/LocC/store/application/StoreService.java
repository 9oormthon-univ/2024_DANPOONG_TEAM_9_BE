package com.goorm.LocC.store.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.review.repository.ReviewRepository;
import com.goorm.LocC.searchHistory.domain.SearchHistory;
import com.goorm.LocC.searchHistory.repository.SearchHistoryRepository;
import com.goorm.LocC.store.domain.*;
import com.goorm.LocC.store.dto.NearStoreInfoDto;
import com.goorm.LocC.store.dto.StoreInfoDto;
import com.goorm.LocC.store.dto.condition.NearStoreCond;
import com.goorm.LocC.store.dto.response.DetailStoreRespDto;
import com.goorm.LocC.store.dto.response.DetailStoreRespDto.SimpleReviewInfo;
import com.goorm.LocC.store.dto.response.ToggleStoreBookmarkRespDto;
import com.goorm.LocC.store.exception.StoreException;
import com.goorm.LocC.store.repository.BusinessHourRepository;
import com.goorm.LocC.store.repository.StoreBookmarkRepository;
import com.goorm.LocC.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.goorm.LocC.store.exception.StoreErrorCode.STORE_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final StoreBookmarkRepository storeBookmarkRepository;
    private final BusinessHourRepository businessHourRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final ReviewRepository reviewRepository;

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

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }

    public List<StoreInfoDto> findStores(List<Category> category, Province province, City city, String storeName, String sortBy, String email) {
        if (category != null && category.size() > 2) {
            throw new IllegalArgumentException("최대 2개의 카테고리만 선택할 수 있습니다.");
        }

        Member member = findMemberByEmail(email);
        if (!storeName.isEmpty()) {
            SearchHistory searchHistory = new SearchHistory(member, storeName);
            searchHistoryRepository.save(searchHistory);
        }

        List<Store> stores = storeRepository.findStoresByFilters(category, province, city, storeName, Sort.by(sortBy));

        DayOfWeek now = LocalDate.now().getDayOfWeek();
        List<BusinessHour> businessHours = businessHourRepository.findBusinessHourByStoreInAndDayOfWeek(stores, now);
        // 가게, 영업 시간 매핑
        Map<Store, BusinessHour> storeBusinessHourMap = businessHours.stream()
                .collect(Collectors.toMap(
                        BusinessHour::getStore, // key: Store
                        bh -> bh // value: BusinessHour
                ));

        return storeBusinessHourMap.entrySet().stream()
                .map(e -> StoreInfoDto.of(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public DetailStoreRespDto findById(Long storeId, String email) {
        Member member = findMemberByEmail(email);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(STORE_NOT_FOUND));

        // 가게 저장 여부 확인
        boolean isStoreBookmarked = storeBookmarkRepository.existsByMemberAndStore(member, store);

        // 가게 주간 영업 시간 정보 조회(월~일)
        List<DetailStoreRespDto.BusinessHourInfo> businessHours = businessHourRepository.findBusinessHourByStoreOrderByDayOfWeek(store)
                .stream()
                .sorted(Comparator.comparingInt(b -> b.getDayOfWeek().getValue())) // 월 -> 일 순으로 정렬
                .map(DetailStoreRespDto.BusinessHourInfo::from)
                .toList();

        List<SimpleReviewInfo> reviews = reviewRepository.findSimpleReviewDtosByStore(store);

        // 주변 가게 조회
        NearStoreCond condition = NearStoreCond.builder()
                .province(store.getProvince())
                .excludeStore(store) // 현재 가게 제외
                .limit(3) // 3개 제한
                .build();
        List<Store> stores = storeRepository.findNearStoresByProvince(condition);

        // 주변 가게 오늘 영업 시간 조회
        DayOfWeek now = LocalDate.now().getDayOfWeek();
        List<BusinessHour> nearStoresBusinessHours = businessHourRepository.findBusinessHourByStoreInAndDayOfWeek(stores, now);

        // 가게, 영업 시간 매핑
        Map<Store, BusinessHour> storeBusinessHourMap = nearStoresBusinessHours.stream()
                .collect(Collectors.toMap(
                        BusinessHour::getStore, // key: Store
                        bh -> bh // value: BusinessHour
                ));

        List<NearStoreInfoDto> nearbyStores = storeBusinessHourMap.entrySet().stream()
                .map(e ->
                        NearStoreInfoDto.of(e.getKey(), e.getValue(),
                                storeBookmarkRepository.existsByMemberAndStore(member, e.getKey())))
                .toList();

        return DetailStoreRespDto.of(store, isStoreBookmarked, businessHours, reviews, nearbyStores);
    }
}