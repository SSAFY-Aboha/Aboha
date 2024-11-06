package com.ssafy.aboha.user.repository;

import com.ssafy.aboha.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByNickname(String nickname);

    boolean existsUserByEmail(String email);

}
