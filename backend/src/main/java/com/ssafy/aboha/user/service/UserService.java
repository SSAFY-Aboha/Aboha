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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
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
        log.info("회원가입 완료: {}", user.getId());
    }

    /**
     * 닉네임 중복 확인
     */
    @Transactional(readOnly = true)
    public UniqueResponse isNicknameAvailable(String nickname) {
        boolean isAvailable = !userRepository.existsUserByNickname(nickname);
        return new UniqueResponse(isAvailable);
    }

    /**
     * 이메일 중복 확인
     */
    @Transactional(readOnly = true)
    public UniqueResponse isEmailAvailable(String email) {
        boolean isAvailable = !userRepository.existsUserByEmail(email);
        return new UniqueResponse(isAvailable);
    }

    /**
     * 회원 정보 수정 및 최신 데이터 반환
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserInfo updateUser(Integer id, UserUpdateRequest request) {
        // 1. 사용자 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 2. 닉네임 업데이트
        if (request.nickname() != null) {
            if (!user.getNickname().equals(request.nickname())) {
                verifyUniqueNickname(request.nickname());
                user.updateNickname(request.nickname());
                log.info("닉네임 업데이트 완료: {}", request.nickname());
            }
        }

        // 3. 비밀번호 업데이트
        if (request.password() != null) {
            String encodedPassword = passwordEncoder.encode(request.password());
            user.updatePassword(encodedPassword);
            log.info("비밀번호 업데이트 완료.");
        }

        // 4. 프로필 이미지 업데이트
        if (request.profileImage() != null && !request.profileImage().isEmpty()) {
            MultipartFile profileImage = request.profileImage();
            String existingImageUrl = user.getProfileImageUrl();

            String newProfileImageUrl = profileImageService.saveImage(profileImage);
            user.updateProfileImageUrl(newProfileImageUrl);
            log.info("프로필 이미지 URL 업데이트 완료: {}", newProfileImageUrl);

            if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
                // 트랜잭션 커밋 후 기존 이미지 삭제 작업을 등록
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        profileImageService.deleteExistingImage(existingImageUrl);
                        log.info("기존 이미지 삭제 완료: {}", existingImageUrl);
                    }

                    // 필요한 경우 다른 메서드도 오버라이드 가능
                });
            }
        }

        // 변경 사항은 트랜잭션 커밋 시 자동으로 저장됩니다.
        log.info("회원 정보 수정 완료: {}", user.getId());

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
        log.info("회원 탈퇴 완료: {}", user.getId());
    }

    /**
     * 닉네임 중복 검증
     */
    private void verifyUniqueNickname(String nickname) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw new ConflictException("이미 존재하는 닉네임입니다.");
        }
    }

    /**
     * 이메일 중복 검증
     */
    private void verifyUniqueEmail(String email) {
        if (userRepository.existsUserByEmail(email)) {
            throw new ConflictException("이미 회원가입한 이메일입니다.");
        }
    }
}
