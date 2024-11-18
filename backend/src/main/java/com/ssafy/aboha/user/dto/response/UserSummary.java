package com.ssafy.aboha.user.dto.response;

import com.ssafy.aboha.user.domain.User;
import lombok.Builder;

@Builder
public record UserSummary(
    Integer id,
    String nickname,
    String profileImageUrl
) {

    public static UserSummary from(User user) {
        return UserSummary.builder()
            .id(user.getId())
            .nickname(user.getNickname())
            .profileImageUrl(user.getProfileImageUrl())
            .build();
    }

}
