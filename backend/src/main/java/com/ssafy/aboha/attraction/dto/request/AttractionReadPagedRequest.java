package com.ssafy.aboha.attraction.dto.request;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record AttractionReadPagedRequest(
    @Nullable Integer sidoCode,
    @Nullable Integer gugunCode,
    @Nullable Integer contentTypeId,
    @Nullable String keyword,
    @Nullable String sort, // 정렬 조건
    @Nullable Long lastSortValue, // 마지막으로 조회된 정렬 값
    @Nullable Integer lastId // 마지막으로 조회된 레코드의 ID
) {
        /**
         * 정렬 조건이 null이거나 비어있을 경우 기본값 "NEW"를 반환합니다.
         *
         * @return 정렬 조건 또는 기본값
         */
        public String getSortOrDefault() {
                return (sort == null || sort.isBlank()) ? "NEW" : sort.toUpperCase();
        }
}
