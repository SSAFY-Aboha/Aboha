package com.ssafy.aboha.user.dto.response;

import com.ssafy.aboha.common.utils.ServletUtils;
import com.ssafy.aboha.user.domain.User;
import lombok.Builder;

@Builder
public record UserSummary(
    Integer id,
    String nickname,
    String profileImageUrl
) {
    private static final String CONTEXT_PATH = ServletUtils.getContextPath();

    public static UserSummary from(User user) {
        String profileImageUrl = user.getProfileImageUrl() != null
                ? CONTEXT_PATH + user.getProfileImageUrl()
                :null;

        return UserSummary.builder()
            .id(user.getId())
            .nickname(user.getNickname())
            .profileImageUrl(profileImageUrl)
            .build();
    }

}
