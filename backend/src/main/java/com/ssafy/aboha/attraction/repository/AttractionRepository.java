package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttractionRepository extends JpaRepository<Attraction, Integer>, JpaSpecificationExecutor<Attraction> {
}

