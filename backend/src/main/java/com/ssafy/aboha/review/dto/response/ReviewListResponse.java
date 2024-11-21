package com.ssafy.aboha.review.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ReviewListResponse(
        List<ReviewResponse> data,
        Long totalCount,
        BigDecimal totalAvgRating
) {

    public static ReviewListResponse of(List<ReviewResponse> data, Long totalCount, BigDecimal totalAvgRating) {
        return ReviewListResponse.builder()
                .data(data)
                .totalAvgRating(totalAvgRating)
                .totalCount(totalCount)
                .build();
    }
}
