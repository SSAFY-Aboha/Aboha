package com.ssafy.aboha.abog.repository;

import com.ssafy.aboha.abog.domain.AbogImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbogImageRepository extends JpaRepository<AbogImage, Integer> {

    List<AbogImage> findByAbogId(Integer abogId);

}
