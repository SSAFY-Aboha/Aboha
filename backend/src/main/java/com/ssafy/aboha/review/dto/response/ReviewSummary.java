package com.ssafy.aboha.review.dto.response;

import com.ssafy.aboha.review.domain.Review;
import lombok.Builder;

@Builder
public record ReviewSummary(
    // 리뷰
    Integer id,
    Integer rating,
    String content
) {

    public static ReviewSummary from(Review review) {
        return ReviewSummary.builder()
            .id(review.getId())
            .rating(review.getRating())
            .content(review.getContent())
            .build();
    }

}
