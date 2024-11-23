package com.goorm.LocC.searchHistory.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.domain.PreferredCategory;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.searchHistory.domain.SearchHistory;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto.RecentKeywordInfoDto;
import com.goorm.LocC.searchHistory.exception.SearchHistoryException;
import com.goorm.LocC.searchHistory.repository.RecommendedKeywordCond;
import com.goorm.LocC.searchHistory.repository.SearchHistoryRepository;
import com.goorm.LocC.store.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.goorm.LocC.searchHistory.exception.SearchHistoryErrorCode.SEARCH_HISTORY_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchHistoryService {

    private final MemberRepository memberRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    public SearchKeywordRespDto getSearchKeywordInfo(String email) {
        Member member = findMemberByEmail(email);

        List<String> popularKeywords = searchHistoryRepository.findPopularKeywordsTop10();
        List<SearchHistory> searchHistories = searchHistoryRepository
                .findAllByMemberAndIsDeletedOrderByCreatedAtDesc(member, false);

        if (searchHistories.size() > 3) {
            searchHistories.stream()
                    .skip(3)
                    .forEach(SearchHistory::delete);
        }

        List<RecentKeywordInfoDto> recentKeywords = searchHistories.stream()
                .limit(3)
                .map(RecentKeywordInfoDto::from)
                .toList();

        // 멤버의 선호 지역과 카테고리를 기반으로 같은 사용자들이 많이 검색한 키워드를 추천
        List<Category> preferredCategories = member.getPreferredCategories().stream()
                .map(PreferredCategory::getCategory)
                .toList();

        List<String> recommendedKeywords = searchHistoryRepository.getRecommendedKeyword(
                new RecommendedKeywordCond(member.getPreferredProvince(), preferredCategories, 7)
        );

//        if (preferredProvince != null) {
//            recommendedKeywords = List.of(
//                    preferredProvince + " 카페",
//                    preferredProvince + " 숙소",
//                    preferredProvince + " 맛집",
//                    preferredProvince + " 레포츠",
//                    preferredProvince + " 기념품",
//                    preferredProvince + " 액티비티",
//                    preferredProvince + " 놀거리");
//        } else {
//            recommendedKeywords = List.of(
//                    "파주 스테이",
//                    "파주 숙소",
//                    "파주 맛집",
//                    "단양 숙소",
//                    "단양 맛집",
//                    "단양 기념품",
//                    "부산 카페");
//        }

        return SearchKeywordRespDto.builder()
                .popularKeywords(popularKeywords)
                .recentKeywords(recentKeywords)
                .recommendedKeywords(recommendedKeywords)
                .build();
    }

    public void deleteBySearchHistoryId(String email, Long searchHistoryId) {
        Member member = findMemberByEmail(email);

        searchHistoryRepository.findSearchHistoryByMemberAndSearchHistoryId(member, searchHistoryId)
                .orElseThrow(() -> new SearchHistoryException(SEARCH_HISTORY_NOT_FOUND))
                .delete();
    }

    public void deleteAll(String email) {
        Member member = findMemberByEmail(email);

        List<SearchHistory> searchHistories = searchHistoryRepository.findAllByMemberAndIsDeletedOrderByCreatedAtDesc(member, false);
        searchHistories.forEach(SearchHistory::delete);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}
