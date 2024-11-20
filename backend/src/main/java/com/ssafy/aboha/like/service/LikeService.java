package com.ssafy.aboha.like.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.like.domain.AttractionLike;
import com.ssafy.aboha.like.dto.LikeResponse;
import com.ssafy.aboha.like.repository.AttractionLikeRepository;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final AttractionLikeRepository attractionLikeRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;

    @Transactional
    public LikeResponse toggleLike(Integer userId, Integer attractionId) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 관광지 확인
        Attraction attraction = attractionRepository.findByAttractionId(attractionId)
                .orElseThrow(() -> new NotFoundException("관광지가 존재하지 않습니다."));

        // 좋아요 처리
        Optional<AttractionLike> existingLike = attractionLikeRepository.findByUserAndAttraction(user, attraction);

        if (existingLike.isPresent()) {
            // 좋아요 취소
            attractionLikeRepository.delete(existingLike.get());
            attraction.decreaseLikeCount();
            return LikeResponse.of(false);
        } else {
            // 좋아요 추가
            AttractionLike like = AttractionLike.builder()
                    .user(user)
                    .attraction(attraction)
                    .build();
            attractionLikeRepository.save(like);
            attraction.increaseLikeCount();
            return LikeResponse.of(true);
        }
    }

}
