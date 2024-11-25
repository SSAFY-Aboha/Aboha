package com.ssafy.aboha.user.dto.response;

import com.ssafy.aboha.common.utils.ServletUtils;
import com.ssafy.aboha.user.domain.User;
import lombok.Builder;

@Builder
public record UserInfo(Integer id, String nickname, String email, String profileImageUrl) {
    private static final String CONTEXT_PATH = ServletUtils.getContextPath();

    public static UserInfo from(User user) {
        String profileImageUrl = user.getProfileImageUrl() != null
                ? CONTEXT_PATH + user.getProfileImageUrl()
                :null;

        return UserInfo.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
