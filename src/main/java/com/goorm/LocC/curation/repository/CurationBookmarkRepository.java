package com.goorm.LocC.curation.repository;

import com.goorm.LocC.curation.domain.Curation;
import com.goorm.LocC.curation.domain.CurationBookmark;
import com.goorm.LocC.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurationBookmarkRepository extends JpaRepository<CurationBookmark, Long>, CurationBookmarkRepositoryCustom {
    Optional<CurationBookmark> findCurationBookmarkByMemberAndCuration(Member member, Curation cUration);
}
