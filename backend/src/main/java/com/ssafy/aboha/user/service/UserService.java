package com.ssafy.aboha.user.service;

import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.dto.request.SignupRequest;
import com.ssafy.aboha.user.dto.response.UniqueResponse;
import com.ssafy.aboha.user.exception.DuplicateEmailException;
import com.ssafy.aboha.user.exception.DuplicateNicknameException;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /**
     * 회원가입
     */
    @Transactional
    public void signUp(SignupRequest request) {
        verifyUniqueNickname(request.nickname());
        verifyUniqueEmail(request.email());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .nickname(request.nickname())
                .email(request.email())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
    }

    /**
     * 닉네임 중복 확인
     */
    public UniqueResponse isNicknameAvailable(String nickname) {
        boolean isAvailable = !userRepository.existsUserByNickname(nickname);
        return new UniqueResponse(isAvailable);
    }

    /**
     * 이메일 중복 확인
     */
    public UniqueResponse isEmailAvailable(String email) {
        boolean isAvailable = !userRepository.existsUserByEmail(email);
        return new UniqueResponse(isAvailable);
    }

    private void verifyUniqueNickname(String nickname) {
        if(userRepository.existsUserByNickname(nickname)) {
            throw new DuplicateNicknameException("이미 존재하는 닉네임입니다.");
        }
    }

    private void verifyUniqueEmail(String email) {
        if(userRepository.existsUserByEmail(email)) {
            throw new DuplicateEmailException("이미 회원가입한 이메일입니다.");
        }
    }

}
