package com.ssafy.aboha.like.repository;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.like.domain.AttractionLike;
import com.ssafy.aboha.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Integer> {

    Optional<AttractionLike> findByUserAndAttraction(User user, Attraction attraction);

    boolean existsByUserIdAndAttractionId(Integer user_id, Integer attraction_id);

    @Query("SELECT al.attraction.id FROM AttractionLike al WHERE al.user.id = :userId")
    List<Integer> findAttractionIdByUserId(@Param("userId") Integer userId);

}
