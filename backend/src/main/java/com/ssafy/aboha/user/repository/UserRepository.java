package com.ssafy.aboha.user.repository;

import com.ssafy.aboha.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByNickname(String nickname);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);

}
