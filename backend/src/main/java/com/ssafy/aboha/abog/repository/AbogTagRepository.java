package com.ssafy.aboha.abog.repository;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.AbogTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbogTagRepository extends JpaRepository<AbogTag, Integer> {

    List<AbogTag> findByAbog(Abog abog);

    void deleteByAbog(Abog abog);
}
