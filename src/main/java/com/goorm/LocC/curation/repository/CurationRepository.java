package com.goorm.LocC.curation.repository;

import com.goorm.LocC.curation.domain.Curation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CurationRepository extends JpaRepository<Curation, Long> {

    List<Curation> findCurationByPublishedDate(LocalDate publishedDate);
}
