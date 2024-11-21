package com.goorm.LocC.searchHistory.application;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.member.exception.MemberException;
import com.goorm.LocC.member.repository.MemberRepository;
import com.goorm.LocC.searchHistory.domain.SearchHistory;
import com.goorm.LocC.searchHistory.dto.RecentKeywordCond;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto;
import com.goorm.LocC.searchHistory.dto.SearchKeywordRespDto.RecentKeywordInfoDto;
import com.goorm.LocC.searchHistory.exception.SearchHistoryException;
import com.goorm.LocC.searchHistory.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.goorm.LocC.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.goorm.LocC.searchHistory.exception.SearchHistoryErrorCode.SEARCH_HISTORY_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchHistoryService {

    private final MemberRepository memberRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional(readOnly = true)
    public SearchKeywordRespDto getSearchKeywordInfo(String email) {
        Member member = findMemberByEmail(email);
        RecentKeywordCond condition = new RecentKeywordCond(member, 5);

        List<String> popularKeywords = searchHistoryRepository.findPopularKeywordsTop10();
        List<RecentKeywordInfoDto> recentKeywords = searchHistoryRepository.findRecentKeywordsByMember(condition);

        // TODO: 추천 키워드 로직 변경 필요
        String preferredProvince = member.getPreferredProvince().getName();
        List<String> recommendedKeywords = new ArrayList<>();

        if (preferredProvince != null) {
            recommendedKeywords = List.of(
                    preferredProvince + " 카페",
                    preferredProvince + " 숙소",
                    preferredProvince + " 맛집");
        } else {
            recommendedKeywords = List.of(
                    "파주 스테이",
                    "파주 숙소",
                    "파주 맛집",
                    "단양 숙소",
                    "단양 맛집",
                    "단양 기념품",
                    "부산 카페");
        }

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

        List<SearchHistory> searchHistories = searchHistoryRepository.findAllByMemberAndIsDeleted(member, false);
        searchHistories.forEach(SearchHistory::delete);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }
}