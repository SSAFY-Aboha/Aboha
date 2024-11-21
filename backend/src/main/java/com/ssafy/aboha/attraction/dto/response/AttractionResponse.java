package com.ssafy.aboha.attraction.dto.response;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.review.dto.response.ReviewListResponse;
import com.ssafy.aboha.review.dto.response.ReviewResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Builder
public record AttractionResponse(
    Integer id,
    String title,
    Integer sidoCode,
    String sidoName,
    Integer gugunCode,
    String gugunName,
    Integer contentTypeId,
    String contentTypeName,
    String image,
    String address,
    BigDecimal latitude,
    BigDecimal longitude,
    String description,
    Long likeCount,
    Long viewCount,
    ReviewListResponse reviews
) {

    public static AttractionResponse of(Attraction attraction, List<ReviewResponse> reviewData) {
        String image = Optional.ofNullable(attraction.getFirstImage1())
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .orElse(null);
        String address = attraction.getAddr1() + attraction.getAddr2();

        ReviewListResponse reviews = ReviewListResponse.of(reviewData, attraction.getReviewCount(), attraction.calculateAverageRating());

        return AttractionResponse.builder()
            .id(attraction.getId())
            .title(attraction.getTitle())
            .sidoCode(attraction.getSido().getCode())
            .sidoName(attraction.getSido().getName())
            .gugunCode(attraction.getGugun().getCode())
            .gugunName(attraction.getGugun().getName())
            .contentTypeId(attraction.getContentType().getId())
            .contentTypeName(attraction.getContentType().getName())
            .image(image)
            .address(address)
            .latitude(attraction.getLatitude())
            .longitude(attraction.getLongitude())
            .description(attraction.getOverview())
            .likeCount(attraction.getLikeCount())
            .viewCount(attraction.getViewCount())
            .reviews(reviews)
            .build();
    }

}
