package com.ssafy.aboha.auth.controller;

import com.ssafy.aboha.auth.dto.SimpleAuthentication;
import com.ssafy.aboha.auth.dto.request.LoginRequest;
import com.ssafy.aboha.auth.service.AuthService;
import com.ssafy.aboha.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        UserResponse response = authService.authenticate(request);

        // 세션에 사용자 정보 저장
        session.setAttribute("user", response);

        // SecurityContext에 사용자 정보를 동기화
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new SimpleAuthentication(response));

        // SecurityContext를 세션에 저장
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

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

        // SecurityContext에 사용자 정보를 동기화
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null) {
            context.setAuthentication(new SimpleAuthentication(user));
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        }

        return ResponseEntity.ok(user);
    }

}
