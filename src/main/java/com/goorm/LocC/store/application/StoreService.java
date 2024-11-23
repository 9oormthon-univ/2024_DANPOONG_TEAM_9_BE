package com.goorm.LocC.store.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.review.repository.ReviewRepository;
import com.goorm.LocC.searchHistory.repository.SearchHistoryRepository;
import com.goorm.LocC.store.domain.*;
import com.goorm.LocC.store.dto.DetailStoreResp;
import com.goorm.LocC.store.dto.DetailStoreResp.SimpleReviewInfo;
import com.goorm.LocC.store.dto.NearStoreInfoDto;
import com.goorm.LocC.store.dto.StoreInfoDto;
import com.goorm.LocC.store.dto.ToggleStoreBookmarkRespDto;
import com.goorm.LocC.store.exception.StoreException;
import com.goorm.LocC.store.repository.BusinessHourRepository;
import com.goorm.LocC.store.repository.NearStoreCond;
import com.goorm.LocC.store.repository.StoreBookmarkRepository;
import com.goorm.LocC.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<StoreInfoDto> findStores(List<Category> category, Province province, City city, String storeName, String sortBy) {
//        if (category != null && category.size() > 2) {
//            throw new IllegalArgumentException("최대 2개의 카테고리만 선택할 수 있습니다.");
//        }
//
////        Member member = findMemberByEmail(email);
////        if (!storeName.isEmpty()) {
////            SearchHistory searchHistory = new SearchHistory(member, storeName);
////            searchHistoryRepository.save(searchHistory);
////            deleteOldSearchHistory(member);
////        }
//
//        List<Store> stores = storeRepository.findStoresByFilters(category, province, city, storeName, Sort.by(sortBy));
//
//        DayOfWeek now = LocalDate.now().getDayOfWeek();
//        List<BusinessHour> businessHours = businessHourRepository.findBusinessHourByStoreInAndDayOfWeek(stores, now);
//        // 가게, 영업 시간 매핑
//        Map<Store, BusinessHour> storeBusinessHourMap = businessHours.stream()
//                .collect(Collectors.toMap(
//                        BusinessHour::getStore, // Store를 키로 사용
//                        bh -> bh // BusinessHour를 값으로 사용
//                ));
//
//        return stores.stream()
//                .map(store -> {
//                    BusinessHour businessHour = storeBusinessHourMap.get(store);
//                    LocalTime openTime = null;
//                    LocalTime closeTime = null;
//                    Boolean isHoliday = false;
//                    BusinessStatus businessStatus = BusinessStatus.CLOSE;
//
//                    if (businessHour != null) {
//                        openTime = businessHour.getOpenTime();
//                        closeTime = businessHour.getCloseTime();
//                        isHoliday = businessHour.getIsHoliday();
//                        businessStatus = BusinessStatus.checkBusinessStatus(isHoliday, openTime, closeTime);
//                    }
//
//                    return new StoreInfoDto(
//                            store.getStoreId(),
//                            store.getName(),
//                            store.getCategory(),
//                            store.getProvince(),
//                            store.getCity(),
//                            store.getAddress(),
//                            store.getThumbnailImageUrl(),
//                            store.getRating(),
//                            store.getReviewCount(),
//                            openTime,
//                            closeTime,
//                            isHoliday,
//                            businessStatus
//                    );
//                })
//                .collect(Collectors.toList());
        return null;
    }

    public DetailStoreResp findById(Long storeId, String email) {
        Member member = findMemberByEmail(email);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(STORE_NOT_FOUND));

        boolean isStoreBookmarked = storeBookmarkRepository.existsByMemberAndStore(member, store);

        List<DetailStoreResp.BusinessHourInfo> businessHours = businessHourRepository.findBusinessHourByStoreOrderByDayOfWeek(store)
                .stream()
                .sorted(Comparator.comparingInt(b -> b.getDayOfWeek().getValue()))
                .map(DetailStoreResp.BusinessHourInfo::from)
                .toList();

        List<SimpleReviewInfo> reviews = reviewRepository.findSimpleReviewsByStore(store);

        NearStoreCond condition = NearStoreCond.builder()
                .province(store.getProvince())
                .excludeStore(store)
                .limit(3)
                .build();

        List<Store> stores = storeRepository.findStoresByProvince(condition);
        DayOfWeek now = LocalDate.now().getDayOfWeek();
        List<BusinessHour> nearStoresBusinessHours = businessHourRepository.findBusinessHourByStoreInAndDayOfWeek(stores, now);

        // 가게, 영업 시간 매핑
        Map<Store, BusinessHour> storeBusinessHourMap = nearStoresBusinessHours.stream()
                .collect(Collectors.toMap(
                        BusinessHour::getStore, // Store를 키로 사용
                        bh -> bh // BusinessHour를 값으로 사용
                ));

        List<NearStoreInfoDto> nearbyStores = storeBusinessHourMap.entrySet().stream()
                .map(
                    entry -> {
                        boolean isBookmarked = storeBookmarkRepository.existsByMemberAndStore(member, entry.getKey());
                        return NearStoreInfoDto.of(entry.getKey(), entry.getValue(), isBookmarked);
                    }
                )
                .toList();

        return DetailStoreResp.of(store, isStoreBookmarked, businessHours, reviews, nearbyStores);
    }
}