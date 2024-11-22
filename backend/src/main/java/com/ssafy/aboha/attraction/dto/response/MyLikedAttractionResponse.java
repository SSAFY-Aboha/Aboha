package com.ssafy.aboha.attraction.dto.response;

import lombok.Builder;

@Builder
public record MyLikedAttractionResponse(
        Integer likedId,
        Integer attractionId,
        String title,
        String address
) {

    public static MyLikedAttractionResponse of(Integer likedId, Integer id, String title, String address) {
        return MyLikedAttractionResponse.builder()
                .likedId(id)
                .attractionId(id)
                .title(title)
                .address(address)
                .build();
    }
}
