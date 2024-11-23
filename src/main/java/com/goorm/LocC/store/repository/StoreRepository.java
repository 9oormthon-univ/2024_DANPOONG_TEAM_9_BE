package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.domain.Category;
import com.goorm.LocC.store.domain.Province;
import com.goorm.LocC.store.domain.City;
import com.goorm.LocC.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

    // 큐레이션 ID로 스토어 조회
    List<Store> findByCuration_CurationId(Long curationId);

    // 필터링 조건으로 스토어 조회
    @Query("SELECT s FROM Store s WHERE " +
            "(:category IS NULL OR s.category IN :category) AND " +
            "(:province IS NULL OR s.province = :province) AND " +
            "(:city IS NULL OR s.city = :city) AND " +
            "(:storeName IS NULL OR :storeName = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', REPLACE(:storeName, ' ', '%'), '%')))")
    List<Store> findStoresByFilters(
            @Param("category") List<Category> category,
            @Param("province") Province province,
            @Param("city") City city,
            @Param("storeName") String storeName,
            Sort sort
    );
}
