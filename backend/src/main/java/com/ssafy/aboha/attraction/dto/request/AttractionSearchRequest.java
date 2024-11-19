package com.ssafy.aboha.attraction.dto.request;

import jakarta.annotation.Nullable;

public record AttractionSearchRequest(
        @Nullable Integer sidoCode,
        @Nullable Integer gugunCode,
        @Nullable Integer contentTypeId,
        @Nullable String keyword,
        @Nullable String sort // 정렬 조건 추가
) {
        public String getSortOrDefault() {
                return sort == null || sort.isBlank() ? "NEW" : sort.toUpperCase();
        }
}
