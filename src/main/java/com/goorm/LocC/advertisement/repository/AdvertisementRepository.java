package com.goorm.LocC.advertisement.repository;

import com.goorm.LocC.advertisement.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
