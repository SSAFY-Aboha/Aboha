package com.ssafy.aboha.attraction.dto.response;

import com.ssafy.aboha.attraction.domain.Gugun;
import lombok.Builder;

@Builder
public record GugunInfo(
        Integer code,
        String name
) {

    public static GugunInfo from(Gugun gugun) {
        return GugunInfo.builder()
                .code(gugun.getCode())
                .name(gugun.getName())
                .build();
    }
}