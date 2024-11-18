package com.ssafy.aboha.abog.dto.response;

import com.ssafy.aboha.abog.domain.Abog;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AbogResponse(
        // 사용자
        Integer userId,
        String profileImageUrl,
        String nickname,

        // 관광지
        Integer attractionId,
        String attractionTitle,

        // 아보그
        Integer id,
        String title,
        String content,
        Long likeCount,
        Long commentCount,
        LocalDate createdAt,
        LocalDate updatedAt
        // TODO: 이미지 적용 전
) {

    public static AbogResponse from(Abog abog) {
        return AbogResponse.builder()
                .userId(abog.getUser().getId())
                .profileImageUrl(abog.getUser().getProfileImageUrl())
                .nickname(abog.getUser().getNickname())
                .attractionId(abog.getAttraction().getId())
                .attractionTitle(abog.getAttraction().getTitle())
                .id(abog.getId())
                .title(abog.getTitle())
                .content(abog.getContent())
                .likeCount(abog.getLikeCount())
                .commentCount(abog.getCommentCount())
                .createdAt(LocalDate.from(abog.getCreatedAt()))
                .updatedAt(LocalDate.from(abog.getUpdatedAt()))
                .build();
    }

}
