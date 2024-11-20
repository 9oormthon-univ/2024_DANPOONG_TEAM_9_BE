package com.goorm.LocC.store.repository;

import com.goorm.LocC.member.domain.Member;
import com.goorm.LocC.store.domain.Store;
import com.goorm.LocC.store.domain.StoreBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreBookmarkRepository extends JpaRepository<StoreBookmark, Long>, StoreBookmarkRepositoryCustom {

    Optional<StoreBookmark> findStoreBookmarkByMemberAndStore(Member member, Store stroe);
}
