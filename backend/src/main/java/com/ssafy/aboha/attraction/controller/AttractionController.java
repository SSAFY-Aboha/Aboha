package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.dto.request.AttractionSearchRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.service.AttractionService;
import com.ssafy.aboha.common.dto.response.SimplifiedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<SimplifiedResponse<AttractionInfo>> filterAttractions(
            @ModelAttribute AttractionSearchRequest request,
            @PageableDefault(size = 12) Pageable pageable) {
        Slice<AttractionInfo> response = attractionService.getAttractionsByFilters(request, pageable);
        return ResponseEntity.ok().body(SimplifiedResponse.from(response));
    }

}
