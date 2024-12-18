package com.ssafy.aboha.auth.service;

import com.ssafy.aboha.auth.dto.request.LoginRequest;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.dto.response.UserInfo;
import com.ssafy.aboha.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 로그인 정보 확인
     */
    public UserInfo authenticate(LoginRequest request) {
        Optional<User> userOptional = userRepository.findUserByEmail(request.email());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(passwordEncoder.matches(request.password(), user.getPassword())) {
                return UserInfo.from(user);
            }
        }
        throw new UnauthorizedException("아이디나 비밀번호가 일치하지 않습니다.");
    }

}
