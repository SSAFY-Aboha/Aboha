package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import java.util.List;
import java.util.Optional;

public interface AttractionCustomRepository {

    List<Attraction> findByFilters(Integer sidoCode, Integer gugunCode, Integer contentTypeId);

    Optional<Attraction> findByAttractionId(Integer id);
}
