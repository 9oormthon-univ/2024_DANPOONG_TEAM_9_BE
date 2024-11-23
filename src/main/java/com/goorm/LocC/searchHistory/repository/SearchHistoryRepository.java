package com.goorm.LocC.searchHistory.repository;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.searchHistory.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>, SearchHistoryRepositoryCustom {
    List<SearchHistory> findAllByMemberAndIsDeletedOrderByCreatedAtDesc(Member member, Boolean isDeleted);
    Optional<SearchHistory> findSearchHistoryByMemberAndSearchHistoryId(Member member, Long searchHistoryId);
}
