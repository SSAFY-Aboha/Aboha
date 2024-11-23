package com.ssafy.aboha.attraction.dto.response;

import lombok.Builder;

@Builder
public record AttractionInfoWithLiked(
    Integer id,
    String title,
    Integer sidoCode,
    String sidoName,
    Integer gugunCode,
    String gugunName,
    String image,
    Long likeCount,
    Long viewCount,
    Long reviewCount,
    Boolean isLiked
) {

    public static AttractionInfoWithLiked of(AttractionInfo attractionInfo, Boolean isLiked) {
        return AttractionInfoWithLiked.builder()
            .id(attractionInfo.id())
            .title(attractionInfo.title())
            .sidoCode(attractionInfo.sidoCode())
            .sidoName(attractionInfo.sidoName())
            .gugunCode(attractionInfo.gugunCode())
            .gugunName(attractionInfo.gugunName())
            .image(attractionInfo.image())
            .likeCount(attractionInfo.likeCount())
            .viewCount(attractionInfo.viewCount())
            .reviewCount(attractionInfo.reviewCount())
            .isLiked(isLiked)
            .build();
    }

}
