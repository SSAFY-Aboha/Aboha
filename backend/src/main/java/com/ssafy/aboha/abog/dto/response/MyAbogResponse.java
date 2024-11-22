package com.ssafy.aboha.abog.dto.response;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.attraction.domain.Attraction;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MyAbogResponse(
       Integer attractionId,
       String title,
       Integer abogId,
       LocalDate createdAt,
       Long likeCount,
       Long commentCount
) {

    public static MyAbogResponse of(Attraction attraction, Abog abog) {
        return MyAbogResponse.builder()
                .attractionId(attraction.getId())
                .title(attraction.getTitle())
                .abogId(abog.getId())
                .createdAt(abog.getCreatedAt().toLocalDate())
                .likeCount(abog.getLikeCount())
                .commentCount(abog.getCommentCount())
                .build();
    }

}
