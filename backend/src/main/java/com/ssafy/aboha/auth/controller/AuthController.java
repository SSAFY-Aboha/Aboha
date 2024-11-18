package com.ssafy.aboha.auth.controller;

import com.ssafy.aboha.auth.dto.LoginRequest;
import com.ssafy.aboha.auth.service.AuthService;
import com.ssafy.aboha.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        UserResponse response = authService.authenticate(request);
        session.setAttribute("user", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }

    // 세션에 저장된 사용자 정보 확인용 엔드포인트
    @GetMapping("/check-status")
    public ResponseEntity<UserResponse> getSessionUser(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

}
