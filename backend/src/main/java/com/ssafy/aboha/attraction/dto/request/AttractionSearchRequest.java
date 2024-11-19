package com.ssafy.aboha.attraction.dto.request;

import jakarta.annotation.Nullable;

public record AttractionSearchRequest(
        @Nullable
        Integer sidoCode,
        @Nullable
        Integer gugunCode,
        @Nullable
        Integer contentTypeId,
        @Nullable
        String keyword
) {
}
