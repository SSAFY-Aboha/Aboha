package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.dto.request.AttractionSearchRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.service.AttractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<List<AttractionInfo>> filterAttractions(@Valid @ModelAttribute AttractionSearchRequest request) {
        List<AttractionInfo> response = attractionService.getAttractionsByFilters(request);
        return ResponseEntity.ok().body(response);
    }

}
