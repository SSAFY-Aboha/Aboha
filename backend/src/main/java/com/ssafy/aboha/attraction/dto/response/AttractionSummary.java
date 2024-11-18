package com.ssafy.aboha.attraction.dto.response;

import com.ssafy.aboha.attraction.domain.Attraction;
import lombok.Builder;

@Builder
public record AttractionSummary(
    Integer id,
    String title
) {

    public static AttractionSummary from(Attraction attraction) {
        return AttractionSummary.builder()
            .id(attraction.getId())
            .title(attraction.getTitle())
            .build();
    }

}
