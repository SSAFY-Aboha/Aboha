package com.ssafy.aboha.user.repository;

import com.ssafy.aboha.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByNickname(String nickname);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);

    boolean existsById(int id);

}
