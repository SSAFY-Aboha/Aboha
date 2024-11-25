package com.ssafy.aboha.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
        @NotBlank(message = "댓글 내용이 입력되지 않았습니다.")
        String content
) {
}
