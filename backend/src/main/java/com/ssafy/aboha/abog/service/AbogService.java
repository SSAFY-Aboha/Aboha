
package com.ssafy.aboha.abog.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.AbogImage;
import com.ssafy.aboha.abog.dto.request.AbogRequest;
import com.ssafy.aboha.abog.dto.response.AbogResponse;
import com.ssafy.aboha.abog.repository.AbogRepository;
import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.dto.response.UserResponse;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public Integer createAbog(UserResponse userResponse, AbogRequest request) {
        // 사용자 확인
        User user = userRepository.findById(userResponse.id())
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 관광지 확인
        Attraction attraction = attractionRepository.findByAttractionId(request.attractionId())
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
        List<MultipartFile> images = request.images();
        abogImageService.uploadImages(abog, images);

        return abog.getId();
    }

    /**
     * 아보그 생세 조회
     */
    public AbogResponse getAbogById(Integer id) {
        Abog abog = abogRepository.findByAbogId(id)
                .orElseThrow(() -> new NotFoundException("해당 아보그가 존재하지 않습니다."));

        // 아보그 이미지 URL 리스트 조회
        List<String> imageUrls = abogImageService.getAbogImages(id)
            .stream()
            .map(AbogImage::getImageUrl)
            .toList();

        return AbogResponse.from(abog, imageUrls);
    }

}