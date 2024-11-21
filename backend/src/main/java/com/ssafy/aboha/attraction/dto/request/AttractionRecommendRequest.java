package com.ssafy.aboha.attraction.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AttractionRecommendRequest(
        @NotNull(message = "시도 코드가 입력되지 않았습니다.")
        Integer sidoCode,
        @NotNull(message = "구군 코드가 입력되지 않았습니다.")
        Integer gugunCode,
        @NotNull(message = "관광지 유형 ID가 입력되지 않았습니다.")
        Integer contentTypeId,
        @NotNull(message = "분위기 필터가 없습니다.")
        List<String> atmosphere
) {
}
