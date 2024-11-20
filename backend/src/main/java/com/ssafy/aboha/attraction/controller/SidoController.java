package com.ssafy.aboha.attraction.controller;

import com.ssafy.aboha.attraction.dto.response.SidoInfo;
import com.ssafy.aboha.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sidos")
@RequiredArgsConstructor
public class SidoController {

    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<List<SidoInfo>> getSidos() {
        List<SidoInfo> response = attractionService.getSidos();
        return ResponseEntity.ok().body(response);
    }
}
