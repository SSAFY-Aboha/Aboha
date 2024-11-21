package com.ssafy.aboha.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotNull(message = "아보그 ID가 입력되지 않았습니다.")
        Integer abogId,
        // TODO: 대댓글 적용 전
        @NotBlank(message = "댓글 내용이 입력되지 않았습니다.")
        String content
) {
}
