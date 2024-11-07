package com.ssafy.aboha.auth.service;

import com.ssafy.aboha.auth.dto.LoginRequest;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.dto.response.UserResponse;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 로그인 정보 확인
     */
    public UserResponse authenticate(LoginRequest request) {
        Optional<User> userOptional = userRepository.findUserByEmail(request.email());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(passwordEncoder.matches(request.password(), user.getPassword())) {
                return UserResponse.from(user);
            }
        }
        throw new UnauthorizedException("아이디나 비밀번호가 일치하지 않습니다.");
    }

}