package com.ssafy.aboha.user.controller;

import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.user.dto.request.EmailRequest;
import com.ssafy.aboha.user.dto.request.NicknameRequest;
import com.ssafy.aboha.user.dto.request.SignupRequest;
import com.ssafy.aboha.user.dto.request.UserUpdateRequest;
import com.ssafy.aboha.user.dto.response.UniqueResponse;
import com.ssafy.aboha.user.dto.response.UserInfo;
import com.ssafy.aboha.user.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    // 회원 정보 수정 API
    @PutMapping("/me")
    public ResponseEntity<Void> updateUser(
        @Valid @ModelAttribute UserUpdateRequest request,
        HttpSession session
    ) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        UserInfo response = userService.updateUser(user.id(), request);
        session.setAttribute("user", response); // 세션 데이터 갱신
        return ResponseEntity.noContent().build();
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(HttpSession session) {
        // 세션에서 인증된 사용자 정보 확인
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            throw new UnauthorizedException("로그인이 필요합니다."); // 인증 실패
        }

        userService.deleteUser(user.id());
        return ResponseEntity.noContent().build();
    }
}
