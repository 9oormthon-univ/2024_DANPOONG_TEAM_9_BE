package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
    List<Store> findByCuration_CurationId(Long curationId); // 큐레이션 ID로 스토어 조회

}