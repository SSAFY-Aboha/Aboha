package com.ssafy.aboha.attraction.dto.response;

import com.ssafy.aboha.attraction.domain.Sido;
import lombok.Builder;

@Builder
public record SidoInfo(
        Integer code,
        String name
) {

    public static SidoInfo from(Sido sido) {
        return SidoInfo.builder()
                .code(sido.getCode())
                .name(sido.getName())
                .build();
    }
}
