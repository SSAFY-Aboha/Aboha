package com.ssafy.aboha.user.service;

import com.ssafy.aboha.common.exception.ConflictException;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.dto.request.SignupRequest;
import com.ssafy.aboha.user.dto.request.UserUpdateRequest;
import com.ssafy.aboha.user.dto.response.UniqueResponse;
import com.ssafy.aboha.user.dto.response.UserInfo;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileImageService profileImageService;

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

    /**
     * 회원 정보 수정 및 최신 데이터 반환
     */
    @Transactional
    public UserInfo updateUser(Integer id, UserUpdateRequest request) {
        // 1. 사용자 조회
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 2. 닉네임 업데이트
        if (request.nickname() != null) {
            verifyUniqueNickname(request.nickname());
            user.updateNickname(request.nickname());
        }

        // 3. 비밀번호 업데이트
        if (request.password() != null) {
            String encodedPassword = passwordEncoder.encode(request.password());
            user.updatePassword(encodedPassword);
        }

        // 4. 프로필 이미지 업데이트
        if (request.profileImage() != null) {
            String profileImageUrl = profileImageService.handleProfileImage(request.profileImage(), user.getProfileImageUrl());
            log.info(profileImageUrl);
            user.updateProfileImageUrl(profileImageUrl);
        }

        // 데이터베이스 동기화
        userRepository.flush();

        return UserInfo.from(user);
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        user.delete();
    }

    private void verifyUniqueNickname(String nickname) {
        if(userRepository.existsUserByNickname(nickname)) {
            throw new ConflictException("이미 존재하는 닉네임입니다.");
        }
    }

    private void verifyUniqueEmail(String email) {
        if(userRepository.existsUserByEmail(email)) {
            throw new ConflictException("이미 회원가입한 이메일입니다.");
        }
    }

}
