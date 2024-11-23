package com.ssafy.aboha.like.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.like.domain.AttractionLike;
import com.ssafy.aboha.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Integer> {

    Optional<AttractionLike> findByUserAndAttraction(User user, Attraction attraction);

    boolean existsByUserIdAndAttractionId(Integer user_id, Integer attraction_id);

}
