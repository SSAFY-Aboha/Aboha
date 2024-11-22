package com.ssafy.aboha.user.controller;

import com.ssafy.aboha.attraction.dto.response.MyLikedAttractionResponse;
import com.ssafy.aboha.attraction.dto.response.MyReviewedAttractionResponse;
import com.ssafy.aboha.attraction.service.AttractionService;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MyPageController {

    private final AttractionService attractionService;

    // 사용자가 좋아요한 관광지 목록 조회
    @GetMapping("/likes")
    public ResponseEntity<KeySetPaginatedResponse<MyLikedAttractionResponse>> getUserLikedAttractions(
            HttpSession session,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserResponse userResponse = (UserResponse) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        KeySetPaginatedResponse<MyLikedAttractionResponse> response = attractionService.getUserLikedAttractions(userResponse.id(), pageable);
        return ResponseEntity.ok().body(response);
    }

    // 사용자가 남긴 리뷰 목록 조회
    @GetMapping("/reviews")
    public ResponseEntity<KeySetPaginatedResponse<MyReviewedAttractionResponse>> getUserReviewedAttractions(
            HttpSession session,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserResponse userResponse = (UserResponse) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        KeySetPaginatedResponse<MyReviewedAttractionResponse> response = attractionService.getUserReviewedAttractions(userResponse.id(), pageable);
        return ResponseEntity.ok().body(response);
    }
}
