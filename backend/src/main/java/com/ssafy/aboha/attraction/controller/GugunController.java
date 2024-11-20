package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.dto.response.GugunInfo;
import com.ssafy.aboha.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guguns")
@RequiredArgsConstructor
public class GugunController {

    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<List<GugunInfo>> getGuguns(@RequestParam("sidoCode") Integer sidoCode) {
        List<GugunInfo> response = attractionService.getGuguns(sidoCode);
        return ResponseEntity.ok().body(response);
    }
}
