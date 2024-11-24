package com.ssafy.aboha.user.dto.response;

import lombok.Builder;

@Builder
public record MyPageResposne(
    UserInfo user,
    Long likeCount,
    Long reviewCount,
    Long abogCount
) {

    public static MyPageResposne of(UserInfo user, Long likeCount, Long reviewCount, Long abogCount) {
        return MyPageResposne.builder()
            .user(user)
            .likeCount(likeCount)
            .reviewCount(reviewCount)
            .abogCount(abogCount)
            .build();
    }
}
