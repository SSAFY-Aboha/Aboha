package com.ssafy.aboha.like.repository;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.like.domain.AbogLike;
import com.ssafy.aboha.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbogLikeRepository extends JpaRepository<AbogLike, Integer> {

    Optional<AbogLike> findByUserAndAbog(User user, Abog abog);
}
