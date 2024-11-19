package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface AttractionCustomRepository {

    Slice<Attraction> findByFilters(Integer sidoCode, Integer gugunCode, Integer contentTypeId, String keyword, Pageable pageable);

    Optional<Attraction> findByAttractionId(Integer id);
}
