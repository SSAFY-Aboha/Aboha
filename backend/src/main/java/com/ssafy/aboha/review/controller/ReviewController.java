package com.ssafy.aboha.review.controller;

import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.review.dto.request.ReviewRequest;
import com.ssafy.aboha.review.dto.request.ReviewUpdateRequest;
import com.ssafy.aboha.review.dto.response.ReviewResponse;
import com.ssafy.aboha.review.service.ReviewService;
import com.ssafy.aboha.user.dto.response.UserInfo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
        @Valid @RequestBody ReviewRequest request,
        HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        // 리뷰 생성 후 ID 반환
        ReviewResponse response = reviewService.createReview(userResponse.id(), request);

        return ResponseEntity.created(null).body(response);
    }

    // 리뷰 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable("id") Integer id) {
        ReviewResponse response = reviewService.getReview(id);
        return ResponseEntity.ok().body(response);
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("id") Integer id,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        reviewService.deleteReview(userResponse.id(), id);

        return ResponseEntity.noContent().build();
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReview(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ReviewUpdateRequest request,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo userResponse = (UserInfo) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        reviewService.updateReview(userResponse.id(), id, request);

        return ResponseEntity.noContent().build();
    }
}
