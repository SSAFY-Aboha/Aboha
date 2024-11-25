package com.ssafy.aboha.abog.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.AbogTag;
import com.ssafy.aboha.abog.repository.AbogRepository;
import com.ssafy.aboha.abog.repository.AbogTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbogTagService {

    private final AbogTagRepository abogTagRepository;
    private final AbogRepository abogRepository;

    /**
     *  태그 리스트 생성 및 저장
     */
    @Transactional
    public void createTags(Abog abog, List<String> tags) {
        for(String tag : tags) {
            AbogTag abogTag = AbogTag.builder()
                    .abog(abog)
                    .name(tag)
                    .build();
            abogTagRepository.save(abogTag);
        }
    }

    /**
     * 태그 리스트 조회
     */
    public List<String> getTags(Abog abog) {
        return abogTagRepository.findByAbog(abog)
                .stream()
                .map(AbogTag::getName)
                .toList();
    }

    /**
     * 태그 리스트 삭제
     */
    @Transactional
    public void deleteTags(Abog abog) {
        abogTagRepository.deleteByAbog(abog);
    }

}