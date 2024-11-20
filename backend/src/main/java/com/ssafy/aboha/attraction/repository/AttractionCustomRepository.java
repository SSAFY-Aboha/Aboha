package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AttractionCustomRepository {

    PaginatedResponse<AttractionInfo> findByFilters(Integer sidoCode, Integer gugunCode, Integer contentTypeId, String keyword, String sort, Pageable pageable);

    Optional<Attraction> findByAttractionId(Integer id);
}
