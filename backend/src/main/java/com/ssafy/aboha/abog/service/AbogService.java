
package com.ssafy.aboha.abog.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.dto.request.AbogRequest;
import com.ssafy.aboha.abog.dto.request.AbogUpdateRequest;
import com.ssafy.aboha.abog.dto.response.AbogResponse;
import com.ssafy.aboha.abog.dto.response.MyAbogResponse;
import com.ssafy.aboha.abog.repository.AbogRepository;
import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.like.repository.AbogLikeRepository;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.dto.response.UserInfo;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbogService {

    private final AbogRepository abogRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;
    private final AbogImageService abogImageService;
    private final AbogLikeRepository likeRepository;
    private final AbogTagService abogTagService;


    @Transactional
    public Integer createAbog(UserInfo userResponse, AbogRequest request) {
        // 사용자 확인
        User user = userRepository.findById(userResponse.id())
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 관광지 확인
        Attraction attraction = attractionRepository.findByAttractionId(request.attraction())
                .orElseThrow(() -> new NotFoundException("관광지가 존재하지 않습니다."));

        // 아보그 생성
        Abog abog = Abog.builder()
                .user(user)
                .attraction(attraction)
                .title(request.title())
                .content(request.content())
                .build();

        abogRepository.save(abog);

        // 아보그 이미지 생성
        List<MultipartFile> images = request.imageFiles();
        abogImageService.uploadImages(abog, images);

        // 아보그 태그 생성 및 저장
        List<String> tags = request.tags();
        abogTagService.createTags(abog, tags);

        return abog.getId();
    }

    /**
     * 아보그 목록 조회
     */
    public List<AbogResponse> getAbogs(Integer loginId) {
        // 모든 아보그 데이터 조회
        List<Abog> abogs = abogRepository.findAll();

        // 로그인한 사용자가 좋아요한 아보그 ID 리스트 조회
        List<Integer> likedAbogIds = (loginId != null)
                ? likeRepository.findAbogIdByUserId(loginId)
                : List.of();

        // 아보그 데이터를 응답 형태로 변환
        return abogs.stream()
                .map(abog -> {
                    // 각 아보그의 이미지 URL 리스트 조회
                    List<String> imageUrls = abogImageService.getImages(abog);

                    // 각 아보그의 태그 리스트 조회
                    List<String> tags = abogTagService.getTags(abog);

                    // 좋아요 여부 판단
                    boolean isLiked = likedAbogIds.contains(abog.getId());

                    // 아보그 데이터를 AbogResponse로 변환
                    return AbogResponse.of(abog, imageUrls, tags, isLiked);
                })
                .toList();
    }

    /**
     * 아보그 생세 조회
     */
    public AbogResponse getAbogById(Integer id, Integer loginId) {
        Abog abog = abogRepository.findByAbogId(id)
                .orElseThrow(() -> new NotFoundException("해당 아보그가 존재하지 않습니다."));

        // 아보그 이미지 URL 리스트 조회
        List<String> imageUrls = abogImageService.getImages(abog);

        // 아보그 태그 리스트 조회
        List<String> tags = abogTagService.getTags(abog);

        // 좋아요 여부 확인
        boolean isLiked = (loginId != null) && likeRepository.existsByUserIdAndAbogId(loginId, id);

        return AbogResponse.of(abog, imageUrls, tags, isLiked);
    }

    /**
     * 아보그 수정
     */
    @Transactional
    public void updateAbog(Integer userId, Integer id, AbogUpdateRequest request) {
        // 1. 아보그 조회
        Abog abog = abogRepository.findByAbogId(id)
                .orElseThrow(() -> new NotFoundException("아보그를 찾을 수 없습니다."));

        // 2. 사용자 권한 확인
        if (!abog.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("아보그 수정 권한이 없습니다.");
        }

        // 3. 제목, 내용 수정
        abog.update(request.title(), request.content());

        // 4. 기존 이미지 삭제 및 새 이미지 업로드
        abogImageService.deleteImages(abog);
        abogImageService.uploadImages(abog, request.imageFiles());

        // 5. 기존 태그 삭제 및 새 태그 생성
        abogTagService.deleteTags(abog);
        abogTagService.createTags(abog, request.tags());
    }

    /**
     * 아보그 삭제
     */
    @Transactional
    public void deleteAbog(Integer userId, Integer id) {
        // 1. 아보그 조회
        Abog abog = abogRepository.findByAbogId(id)
                .orElseThrow(() -> new NotFoundException("해당 아보그가 존재하지 않습니다."));

        // 2. 사용자 권한 확인
        if (!abog.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("삭제 권한이 없습니다.");
        }

        // 3. 이미지 삭제
        abogImageService.deleteImages(abog);

        // 4. 태그 삭제
        abogTagService.deleteTags(abog);

        // 5. 아보그 삭제 (하위 엔티티는 DB에서 자동 삭제)
        abog.delete();
    }

    /**
     * 사용자의 아보그 목록 조회
     */
    public KeySetPaginatedResponse<MyAbogResponse> getUserAbogs(Integer userId, Pageable pageable) {
        // 1. 사용자 존재 여부 확인
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new NotFoundException("로그인한 사용자가 존재하지 않습니다.");
        }

        // 2. Repository를 통해 좋아요한 관광지 목록 조회
        return abogRepository.findByUserAbog(userId, pageable);
    }

    public Long getCountByUserAbog(Integer userId) {
        return abogRepository.countUserAbog(userId);
    }
}
