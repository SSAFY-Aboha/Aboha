package com.ssafy.aboha.review.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 리뷰 목록 조회
    List<Review> findByAttraction(Attraction attraction);
}