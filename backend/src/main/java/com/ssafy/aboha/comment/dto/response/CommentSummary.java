package com.ssafy.aboha.comment.dto.response;

import com.ssafy.aboha.comment.domain.Comment;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentSummary(
        String content,
        LocalDate createdAt
) {

    public static CommentSummary from(Comment comment) {
        return CommentSummary.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt().toLocalDate())
                .build();
    }
}
