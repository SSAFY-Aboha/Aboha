package com.ssafy.aboha.review.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.common.exception.ForbiddenException;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.common.exception.UnauthorizedException;
import com.ssafy.aboha.review.domain.Review;
import com.ssafy.aboha.review.dto.request.ReviewRequest;
import com.ssafy.aboha.review.dto.request.ReviewUpdateRequest;
import com.ssafy.aboha.review.dto.response.ReviewResponse;
import com.ssafy.aboha.review.repository.ReviewRepository;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
    public Integer createReview(Integer userId, ReviewRequest request) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 관광지 확인
        Attraction attraction = attractionRepository.findByAttractionId(request.attractionId())
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

    /**
     * 리뷰 목록 조회
     */
    public List<ReviewResponse> getReviews(Attraction attraction) {
        List<Review> reviews = reviewRepository.findByAttraction(attraction);
        return reviews.stream()
                .map(ReviewResponse::from)
                .toList();
    }

    /**
     * 리뷰 상세 조회
     */
    public ReviewResponse getReview(Integer id) {
        // 리뷰 확인
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("리뷰가 존재하지 않습니다."));

        return ReviewResponse.from(review);
    }

    /**
     * 리뷰 삭제
     */
    @Transactional
    public void deleteReview(Integer userId, Integer id) {
        // 1. 리뷰 조회
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("리뷰를 찾을 수 없습니다."));

        // 2. 작성자 확인
        if(!review.getUser().getId().equals(userId)) {
            throw new ForbiddenException("리뷰 삭제 권한이 없습니다.");
        }

        // 3. 관광지 정보 업데이트
        Attraction attraction = review.getAttraction();
        BigDecimal rating = BigDecimal.valueOf(review.getRating());
        attraction.deleteReview(rating);

        // 4 리뷰 삭제
        review.delete();
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    public void updateReview(Integer userId, Integer id, ReviewUpdateRequest request) {
        // 1. 리뷰 조회
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("리뷰를 찾을 수 없습니다."));

        // 2. 사용자 권한 확인
        if (!review.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("리뷰 수정 권한이 없습니다.");
        }

        // 3. 리뷰 평점 및 내용 업데이트
        review.update(request.rating(), request.content());

        // 4. 관광지 정보 업데이트
        Attraction attraction = review.getAttraction();
        BigDecimal oldRating = BigDecimal.valueOf(review.getRating());
        BigDecimal newRating = BigDecimal.valueOf(request.rating());
        attraction.updateReview(oldRating, newRating);

        review.update(request.rating(), request.content());
    }

}
