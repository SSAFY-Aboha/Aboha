package com.ssafy.aboha.user.dto.response;

import com.ssafy.aboha.user.domain.User;
import lombok.Builder;

@Builder
public record UserInfo(Integer id, String nickname, String email, String profileImageUrl) {

    public static UserInfo from(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
