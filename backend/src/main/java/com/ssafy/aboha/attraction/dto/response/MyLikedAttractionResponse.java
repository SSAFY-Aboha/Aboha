package com.ssafy.aboha.attraction.dto.response;

import lombok.Builder;

@Builder
public record MyLikedAttractionResponse(
        Integer id,
        String title,
        String address
) {

    public static MyLikedAttractionResponse of(Integer id, String title, String address) {
        return MyLikedAttractionResponse.builder()
                .id(id)
                .title(title)
                .address(address)
                .build();
    }
}
