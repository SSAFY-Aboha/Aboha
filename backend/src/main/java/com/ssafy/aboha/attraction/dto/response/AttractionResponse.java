package com.ssafy.aboha.attraction.dto.response;

import com.ssafy.aboha.attraction.domain.Attraction;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Builder;

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
    Long reviewCount
    // TODO: description -> Spring AI 적용 + 리뷰 적용 전
) {

    public static AttractionResponse of(Attraction attraction, String description) {
        String image = Optional.ofNullable(attraction.getFirstImage1())
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .orElse(null);
        String address = attraction.getAddr1() + attraction.getAddr2();

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
            .description(description)
            .likeCount(attraction.getLikeCount())
            .viewCount(attraction.getViewCount())
            .reviewCount(attraction.getReviewCount())
            .build();
    }

}
