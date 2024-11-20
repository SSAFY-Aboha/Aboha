package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.dto.request.AttractionReadRequest;
import com.ssafy.aboha.attraction.dto.request.AttractionSearchRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.AttractionResponse;
import com.ssafy.aboha.attraction.dto.response.AttractionSummary;
import com.ssafy.aboha.attraction.service.AttractionService;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.like.dto.LikeResponse;
import com.ssafy.aboha.like.service.LikeService;
import com.ssafy.aboha.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;
    private final LikeService likeService;

    // 관광지 목록 필터링 조회
    @GetMapping
    public ResponseEntity<PaginatedResponse<AttractionInfo>> filterAttractions(
            @ModelAttribute AttractionReadRequest request,
            @PageableDefault(size = 12) Pageable pageable) {
        PaginatedResponse<AttractionInfo> response = attractionService.getAttractionsByFilters(request, pageable);
        return ResponseEntity.ok().body(response);
    }

    // 관광지 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AttractionResponse> getAttractionById(@PathVariable("id") Integer id) {
        AttractionResponse response = attractionService.getAttraction(id);
        return ResponseEntity.ok().body(response);
    }

    // 아보그 생성 시 관광지 이름 검색
    @GetMapping("/search")
    public ResponseEntity<List<AttractionSummary>> searchAttractions(@Valid @ModelAttribute AttractionSearchRequest request) {
        List<AttractionSummary> response = attractionService.getAttractionsByTitle(request.keyword().trim());
        return ResponseEntity.ok().body(response);
    }

    /**
     * Like
     */
    // 관광지 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<LikeResponse> toggleAttractionLike(
            @PathVariable("id") Integer attractionId,
            HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserResponse userResponse = (UserResponse) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        LikeResponse response = likeService.toggleAttractionLike(userResponse.id(), attractionId);
        return ResponseEntity.ok().body(response);
    }

}
