package com.goorm.LocC.curation.repository;

import com.goorm.LocC.curation.domain.Curation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationRepository extends JpaRepository<Curation, Long> {
}
