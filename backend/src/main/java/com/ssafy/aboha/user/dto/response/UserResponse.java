package com.ssafy.aboha.user.dto.response;

import com.ssafy.aboha.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String nickname, String email) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }
}
