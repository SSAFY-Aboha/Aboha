package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.AttractionSummary;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AttractionCustomRepository {

    // 관광지 목록 조회
    PaginatedResponse<AttractionInfo> findAll(Integer sidoCode, Integer gugunCode, Integer contentTypeId, String keyword, String sort, Pageable pageable);
    KeySetPaginatedResponse<AttractionInfo> findAlls(Integer sidoCode, Integer gugunCode, Integer contentTypeId, String keyword, String sort, Long lastSortValue, Integer lastId, Pageable pageable);

    // 관광지 상세 조회
    Optional<Attraction> findByAttractionId(Integer id);

    // 관광지 이름 검색
    List<AttractionSummary> findByTitle(String title);

    // 관광지 목록 필터링 조회
    List<Attraction> findByFilters(Integer sidoCode, List<Integer> gugunCodes, Integer contentTypeId);

    // 사용자가 좋아요한 관광지 목록 조회
    KeySetPaginatedResponse<AttractionInfo> findByUserLiked(Integer userId, Pageable pageable);
}
