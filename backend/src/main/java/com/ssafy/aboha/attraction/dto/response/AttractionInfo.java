package com.ssafy.aboha.attraction.dto.response;

import com.ssafy.aboha.attraction.domain.Attraction;
import lombok.Builder;

import java.util.Optional;

@Builder
public record AttractionInfo(
        Integer id,
        String title,
        Integer sidoCode,
        String sidoName,
        Integer gugunCode,
        String gugunName,
        String image,
        Long likeCount,
        Long viewCount,
        Long reviewCount
) {

    public static AttractionInfo from(Attraction attraction) {
        String image = Optional.ofNullable(attraction.getFirstImage1())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElse(null);

        return AttractionInfo.builder()
                .id(attraction.getId())
                .title(attraction.getTitle())
                .sidoCode(attraction.getSido().getCode())
                .sidoName(attraction.getSido().getName())
                .gugunCode(attraction.getGugun().getCode())
                .gugunName(attraction.getGugun().getName())
                .image(image)
                .likeCount(attraction.getLikeCount())
                .viewCount(attraction.getViewCount())
                .reviewCount(attraction.getReviewCount())
                .build();
    }
}