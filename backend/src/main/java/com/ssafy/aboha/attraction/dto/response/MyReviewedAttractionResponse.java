package com.ssafy.aboha.attraction.dto.response;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.review.domain.Review;
import lombok.Builder;

@Builder
public record MyReviewedAttractionResponse(
        Integer attractionId,
        String title,
        Integer reviewId,
        String content,
        Integer rating
) {
    public static MyReviewedAttractionResponse of(Attraction attraction, Review review) {
        return MyReviewedAttractionResponse.builder()
                .attractionId(attraction.getId())
                .title(attraction.getTitle())
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

}
