package com.goorm.LocC.store.repository;

import com.goorm.LocC.store.domain.BusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, Long> {
}
