package com.ssafy.aboha.attraction.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AttractionSearchRequest(
        @NotBlank(message = "검색 키워드는 필수입니다.")
        String keyword
) {}
