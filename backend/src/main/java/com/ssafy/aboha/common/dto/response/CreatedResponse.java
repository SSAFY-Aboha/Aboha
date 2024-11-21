package com.ssafy.aboha.common.dto.response;

import lombok.Builder;

@Builder
public record CreatedResponse(
        boolean isCreated
) {

    public static CreatedResponse of(boolean isCreated) {
        return CreatedResponse.builder()
                .isCreated(isCreated)
                .build();
    }
}
