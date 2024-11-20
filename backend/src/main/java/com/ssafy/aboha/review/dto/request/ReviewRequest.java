package com.ssafy.aboha.review.dto.request;

import jakarta.validation.constraints.*;

public record ReviewRequest(
        @NotNull(message = "평점은 필수입니다.")
        @Min(value = 1, message = "평점은 최소 1점이어야 합니다.")
        @Max(value = 5, message = "평점은 최대 5점이어야 합니다.")
        Integer rating,

        @NotBlank(message = "리뷰 내용이 입력되지 않았습니다.")
        @Size(max = 500, message = "리뷰 내용은 최대 500자까지 가능합니다.")
        String content
) {}
