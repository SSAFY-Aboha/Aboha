package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.domain.ContentType;
import com.ssafy.aboha.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contenttypes")
@RequiredArgsConstructor
public class ContentTypeController {

    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<List<ContentType>> getContentTypes() {
        List<ContentType> response = attractionService.getContentTypes();
        return ResponseEntity.ok().body(response);
    }
}
