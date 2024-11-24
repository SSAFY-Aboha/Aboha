
package com.ssafy.aboha.abog.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.AbogImage;
import com.ssafy.aboha.abog.dto.request.AbogRequest;
import com.ssafy.aboha.abog.dto.response.AbogResponse;
import com.ssafy.aboha.abog.dto.response.MyAbogResponse;
import com.ssafy.aboha.abog.repository.AbogRepository;
import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.like.repository.AbogLikeRepository;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.dto.response.UserInfo;
import com.ssafy.aboha.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbogService {

    private final AbogRepository abogRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;
    private final AbogImageService abogImageService;
    private final AbogLikeRepository likeRepository;

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
                List<String> imageUrls = abogImageService.getAbogImages(abog.getId())
                    .stream()
                    .map(AbogImage::getImageUrl)
                    .toList();

                // 좋아요 여부 판단
                boolean isLiked = likedAbogIds.contains(abog.getId());

                // 아보그 데이터를 AbogResponse로 변환
                return AbogResponse.of(abog, imageUrls, isLiked);
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
        List<String> imageUrls = abogImageService.getAbogImages(id)
            .stream()
            .map(AbogImage::getImageUrl)
            .toList();

        // 좋아요 여부 확인
        boolean isLiked = (loginId != null) && likeRepository.existsByUserIdAndAbogId(loginId, id);

        return AbogResponse.of(abog, imageUrls, isLiked);
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

}
