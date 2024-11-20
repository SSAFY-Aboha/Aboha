package com.ssafy.aboha.review.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.review.domain.Review;
import com.ssafy.aboha.review.dto.request.ReviewRequest;
import com.ssafy.aboha.review.repository.ReviewRepository;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;

    /**
     * 리뷰 작성
     */
    @Transactional
    public Integer createReview(Integer userId, Integer attractionId, ReviewRequest request) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 관광지 확인
        Attraction attraction = attractionRepository.findByAttractionId(attractionId)
                .orElseThrow(() -> new NotFoundException("관광지가 존재하지 않습니다."));

        // 리뷰 생성
        Review review = Review.builder()
                .user(user)
                .attraction(attraction)
                .rating(request.rating())
                .content(request.content())
                .build();

        reviewRepository.save(review);

        // 평점 업데이트
        attraction.addReview(BigDecimal.valueOf(request.rating()));
        attractionRepository.save(attraction);

        return review.getId();
    }
}
