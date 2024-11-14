package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Attraction;

import java.util.List;

public interface AttractionCustomRepository {

    List<Attraction> findByFilters(Integer sidoCode, Integer gugunCode, Integer contentTypeId);

}
