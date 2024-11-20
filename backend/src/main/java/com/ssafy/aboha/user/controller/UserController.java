package com.ssafy.aboha.user.controller;

import com.ssafy.aboha.user.dto.request.EmailRequest;
import com.ssafy.aboha.user.dto.request.NicknameRequest;
import com.ssafy.aboha.user.dto.request.SignupRequest;
import com.ssafy.aboha.user.dto.response.UniqueResponse;
import com.ssafy.aboha.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignupRequest request) {
        userService.signUp(request);
        URI uri = URI.create("/");  // 메인 페이지로 이동
        return ResponseEntity.created(uri).build();
    }

    // 닉네임 중복 확인 API
    @GetMapping("/check-nickname")
    public ResponseEntity<UniqueResponse> checkNickname(@Valid @ModelAttribute NicknameRequest request) {
        UniqueResponse response = userService.isNicknameAvailable(request.nickname());
        return ResponseEntity.ok(response);
    }

    // 이메일 중복 확인 API
    @GetMapping("/check-email")
    public ResponseEntity<UniqueResponse> checkEmail(@Valid @ModelAttribute EmailRequest request) {
        UniqueResponse response = userService.isEmailAvailable(request.email());
        return ResponseEntity.ok(response);
    }
}
