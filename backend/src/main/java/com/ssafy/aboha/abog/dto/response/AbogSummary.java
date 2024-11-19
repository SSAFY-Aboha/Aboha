package com.ssafy.aboha.abog.dto.response;

import com.ssafy.aboha.abog.domain.Abog;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record AbogSummary(
    Integer id,
    String title,
    String content,
    Long likeCount,
    Long commentCount,
    LocalDate createdAt,
    LocalDate updatedAt,
    List<String> images
) {

    public static AbogSummary of(Abog abog, List<String> imageUrls) {
        return AbogSummary.builder()
            .id(abog.getId())
            .title(abog.getTitle())
            .content(abog.getContent())
            .likeCount(abog.getLikeCount())
            .commentCount(abog.getCommentCount())
            .createdAt(abog.getCreatedAt().toLocalDate())
            .updatedAt(abog.getUpdatedAt().toLocalDate())
            .images(imageUrls)
            .build();
    }

}
