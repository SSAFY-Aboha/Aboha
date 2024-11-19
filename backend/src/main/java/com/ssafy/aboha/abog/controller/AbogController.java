package com.ssafy.aboha.abog.controller;

import com.ssafy.aboha.abog.dto.request.AbogRequest;
import com.ssafy.aboha.abog.dto.response.AbogResponse;
import com.ssafy.aboha.abog.service.AbogService;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/abogs")
@RequiredArgsConstructor
public class AbogController {

    private final AbogService abogService;

    @PostMapping
    public ResponseEntity<AbogResponse> createAbog(
            @Valid @ModelAttribute AbogRequest request,
            HttpSession session) {
        // 세션에서 인증된 사용자 정보 확인
        UserResponse userResponse = (UserResponse) session.getAttribute("user");
        if (userResponse == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        // 아보그 생성 및 이미지 처리
        AbogResponse response = abogService.createAbog(userResponse, request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/abogs/{id}")
                .buildAndExpand(response.abog().id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbogResponse> getAbogById(@PathVariable(value = "id") Integer id) {
        AbogResponse abogResponse = abogService.getAbogById(id);
        return ResponseEntity.ok(abogResponse);
    }
}
