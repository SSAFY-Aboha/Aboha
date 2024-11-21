package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.dto.request.AttractionRecommendRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.service.AttractionRecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class AttractionRecommendationController {

    private final AttractionRecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<List<AttractionInfo>> getRecommendations(
            @Valid @RequestBody AttractionRecommendRequest request
    ) {
        List<AttractionInfo> response = recommendationService.getRecommendedAttractions(request);
        return ResponseEntity.ok().body(response);
    }
}
