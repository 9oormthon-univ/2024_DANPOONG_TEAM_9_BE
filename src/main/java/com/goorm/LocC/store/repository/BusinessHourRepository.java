package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.domain.BusinessHour;
import com.goorm.LocC.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, Long> {
    List<BusinessHour> findBusinessHourByStoreInAndDayOfWeek(List<Store> stores, DayOfWeek now);
}
