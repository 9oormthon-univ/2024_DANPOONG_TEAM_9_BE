package com.goorm.LocC.searchHistory.repository;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.searchHistory.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>, SearchHistoryRepositoryCustom {
    void deleteAllByMember(Member member);
}
