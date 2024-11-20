package com.ssafy.aboha.like.dto;

import lombok.Builder;

@Builder
public record LikeResponse(
        boolean isLiked // 좋아요 상태
) {

    public static LikeResponse of(boolean isLiked) {
        return LikeResponse.builder()
                .isLiked(isLiked)
                .build();
    }
}

