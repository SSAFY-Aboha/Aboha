package com.ssafy.aboha.review.dto.response;

import com.ssafy.aboha.review.domain.Review;
import com.ssafy.aboha.user.dto.response.UserSummary;
import lombok.Builder;

@Builder
public record ReviewResponse(
    // 사용자
    UserSummary user,

    // 리뷰
    ReviewSummary review
) {

    public static ReviewResponse from(Review review) {
        UserSummary userSummary = UserSummary.from(review.getUser());
        ReviewSummary reviewSummary = ReviewSummary.from(review);

        return ReviewResponse.builder()
            .user(userSummary)
            .review(reviewSummary)
            .build();
    }
}
