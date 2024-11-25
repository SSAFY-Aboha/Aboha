package com.ssafy.aboha.comment.controller;

import com.ssafy.aboha.comment.dto.request.CommentRequest;
import com.ssafy.aboha.comment.dto.request.CommentUpdateRequest;
import com.ssafy.aboha.comment.dto.response.CommentResponse;
import com.ssafy.aboha.comment.service.CommentService;
import com.ssafy.aboha.common.dto.response.CreatedResponse;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.user.dto.response.UserInfo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        // 리뷰 생성 후 ID 반환
        Integer commentId = commentService.createComment(userResponse.id(), request);

        return ResponseEntity.created(null).body(CreatedResponse.of(true));
    }

    // 댓글 목록 조회 -> AbogController

    // 댓글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable("id") Integer id) {
        CommentResponse response = commentService.getComment(id);
        return ResponseEntity.ok().body(response);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable("id") Integer id,
            @Valid @RequestBody CommentUpdateRequest request,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        commentService.updateComment(userResponse.id(), id, request);

        return ResponseEntity.noContent().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("id") Integer id,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        commentService.deleteComment(userResponse.id(), id);

        return ResponseEntity.noContent().build();
    }
}
