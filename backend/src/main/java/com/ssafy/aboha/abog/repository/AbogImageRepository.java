package com.ssafy.aboha.abog.repository;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.AbogImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbogImageRepository extends JpaRepository<AbogImage, Integer> {

    List<AbogImage> findByAbog(Abog abog);

}
