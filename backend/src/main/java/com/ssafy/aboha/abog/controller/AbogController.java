package com.ssafy.aboha.abog.controller;

import com.ssafy.aboha.abog.dto.request.AbogRequest;
import com.ssafy.aboha.abog.dto.request.AbogUpdateRequest;
import com.ssafy.aboha.abog.dto.response.AbogResponse;
import com.ssafy.aboha.abog.service.AbogService;
import com.ssafy.aboha.comment.dto.response.CommentResponse;
import com.ssafy.aboha.comment.service.CommentService;
import com.ssafy.aboha.common.dto.response.CreatedResponse;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.like.dto.LikeResponse;
import com.ssafy.aboha.like.service.LikeService;
import com.ssafy.aboha.user.dto.response.UserInfo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/abogs")
@RequiredArgsConstructor
public class AbogController {

    private final AbogService abogService;
    private final LikeService likeService;
    private final CommentService commentService;

    // 아보그 생성
    @PostMapping
    public ResponseEntity<CreatedResponse> createAbog(
            @Valid @ModelAttribute AbogRequest request,
            HttpSession session) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        // 아보그 생성 및 이미지 처리 후 ID 반환
        Integer abogId = abogService.createAbog(userResponse, request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/abogs/{id}")
                .buildAndExpand(abogId).toUri();

        return ResponseEntity.created(uri).body(CreatedResponse.of(true));
    }

    // 아보그 목록 조회
    @GetMapping
    public ResponseEntity<List<AbogResponse>> getAbogs(HttpSession session) {
        // 세션에서 인증된 사용자 정보 확인 (null 안전 처리)
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        Integer loginId = (userResponse != null) ? userResponse.id() : null;

        List<AbogResponse> response = abogService.getAbogs(loginId);
        return ResponseEntity.ok().body(response);
    }

    // 아보그 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AbogResponse> getAbogById(
        @PathVariable(value = "id") Integer id,
        HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인 (null 안전 처리)
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        Integer loginId = (userResponse != null) ? userResponse.id() : null;

        AbogResponse response = abogService.getAbogById(id, loginId);
        return ResponseEntity.ok(response);
    }

    // 아보그 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAbog(
            @PathVariable(value = "id") Integer id,
            @Valid @RequestBody AbogUpdateRequest request,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        abogService.updateAbog(userResponse.id(), id, request);
        return ResponseEntity.noContent().build();
    }

    // 아보그 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbog(
            @PathVariable(value = "id") Integer id,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        abogService.deleteAbog(userResponse.id(), id);
        return ResponseEntity.noContent().build();
    }

    // 아보그 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<LikeResponse> toggleAbogLike(
            @PathVariable("id") Integer abogId,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        LikeResponse response = likeService.toggleAbogLike(userResponse.id(), abogId);
        return ResponseEntity.ok().body(response);
    }

    // 댓글 목록 조회
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getAbogComments(
            @PathVariable("id") Integer abogId
    ) {
        List<CommentResponse> response = commentService.getComments(abogId);
        return ResponseEntity.ok().body(response);
    }
}
