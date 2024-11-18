
package com.ssafy.aboha.abog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AbogRequest(
        @NotNull(message = "관광지 식별자가 입력되지 않았습니다.")
        Integer attractionId,
        @NotBlank(message = "제목이 입력되지 않았습니다.")
        String title,
        @NotBlank(message = "내용이 입력되지 않았습니다.")
        String content
) {
}
