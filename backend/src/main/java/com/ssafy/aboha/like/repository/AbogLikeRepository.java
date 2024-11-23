package com.ssafy.aboha.like.repository;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.like.domain.AbogLike;
import com.ssafy.aboha.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AbogLikeRepository extends JpaRepository<AbogLike, Integer> {

    Optional<AbogLike> findByUserAndAbog(User user, Abog abog);

    boolean existsByUserIdAndAbogId(Integer userId, Integer abogId);

    // 로그인한 사용자가 좋아요한 Abog ID 목록 반환
    @Query("SELECT al.abog.id FROM AbogLike al WHERE al.user.id = :userId")
    List<Integer> findAbogIdByUserId(Integer userId);

}
