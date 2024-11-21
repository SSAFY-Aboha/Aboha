package com.ssafy.aboha.comment.controller;

import com.ssafy.aboha.comment.dto.request.CommentRequest;
import com.ssafy.aboha.comment.service.CommentService;
import com.ssafy.aboha.common.dto.response.CreatedResponse;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CreatedResponse> createComment(
            @Valid @RequestBody CommentRequest request,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserResponse userResponse = (UserResponse) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        // 리뷰 생성 후 ID 반환
        Integer commentId = commentService.createComment(userResponse.id(), request);

        return ResponseEntity.created(null).body(CreatedResponse.of(true));
    }
}
