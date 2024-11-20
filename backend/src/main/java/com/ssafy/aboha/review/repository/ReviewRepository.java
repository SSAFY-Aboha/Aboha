package com.ssafy.aboha.review.repository;

import com.ssafy.aboha.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
