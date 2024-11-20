package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.dto.request.AttractionReadRequest;
import com.ssafy.aboha.attraction.dto.request.AttractionSearchRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.AttractionResponse;
import com.ssafy.aboha.attraction.dto.response.AttractionSummary;
import com.ssafy.aboha.attraction.service.AttractionService;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

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
}
