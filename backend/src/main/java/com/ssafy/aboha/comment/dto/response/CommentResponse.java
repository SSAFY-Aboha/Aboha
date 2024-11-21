package com.ssafy.aboha.comment.dto.response;

import com.ssafy.aboha.comment.domain.Comment;
import com.ssafy.aboha.user.dto.response.UserSummary;
import lombok.Builder;

@Builder
public record CommentResponse(
        // 사용자
        UserSummary user,

        // 댓글
        CommentSummary comment
) {

    public static CommentResponse from(Comment comment) {
        UserSummary userSummary = UserSummary.from(comment.getUser());
        CommentSummary commentSummary = CommentSummary.from(comment);

        return CommentResponse.builder()
                .user(userSummary)
                .comment(commentSummary)
                .build();
    }
}
