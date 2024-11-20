package com.ssafy.aboha.like.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.like.domain.AttractionLike;
import com.ssafy.aboha.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Integer> {

    Optional<AttractionLike> findByUserAndAttraction(User user, Attraction attraction);
}
