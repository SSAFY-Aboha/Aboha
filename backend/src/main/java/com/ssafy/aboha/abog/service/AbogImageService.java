package com.ssafy.aboha.abog.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.AbogImage;
import com.ssafy.aboha.abog.repository.AbogImageRepository;
import com.ssafy.aboha.common.exception.BadRequestException;
import com.ssafy.aboha.common.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbogImageService {

    private static final Long MAX_FILE_SIZE = 10 * 1024 * 1024L; // 최대 10MB
    private static final int MAX_IMAGES = 5; // 최대 이미지 개수
    private static final String UPLOAD_DIR = "src/main/resources/uploads/abog/"; // 저장 경로
    private static final String BASE_URL = "/uploads/abog/"; // 반환할 URL 경로 prefix

    private final AbogImageRepository abogImageRepository;

    /**
     * 아보그 이미지 등록
     */
    @Transactional
    public void uploadImages(Abog abog, List<MultipartFile> images) {
        try {
            // 이미지 유효성 검사
            validateImage(images);

            // 각 이미지 파일에 대해 업로드 및 DB 저장 수행
            for (MultipartFile image : images) {
                // 이미지 파일 경로 저장
                String dbFilePath = saveImage(image);

                // 엔티티 생성 및 저장
                AbogImage abogImage = AbogImage.builder()
                        .abog(abog)
                        .imageUrl(dbFilePath)
                        .build();

                abogImageRepository.save(abogImage);
            }
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 이미지 파일 저장 및 URL 반환
     */
    private String saveImage(MultipartFile image) throws IOException {
        // UUID 기반 고유 파일명 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();

        // 실제 파일 저장 경로
        Path savePath = Paths.get(UPLOAD_DIR, fileName);

        // 디렉토리 생성 (없으면 생성)
        Files.createDirectories(savePath.getParent());

        // 파일 저장
        Files.write(savePath, image.getBytes());

        // 반환할 URL 경로 (DB에 저장할 경로)
        return BASE_URL + fileName;
    }

    /**
     * 아보그 이미지 리스트 조회
     */
    public List<String> getImages(Abog abog) {
        String contextPath = ServletUtils.getContextPath(); // HTTP 요청 시점에 동적으로 Context Path 가져오기

        return abogImageRepository.findByAbog(abog)
                .stream()
                .map(abogImage -> contextPath + abogImage.getImageUrl()) // Context Path 추가
                .toList();
    }

    /**
     * 아보그 이미지 삭제
     */
    @Transactional
    public void deleteImages(Abog abog) {
        List<AbogImage> images = abogImageRepository.findByAbog(abog);

        for (AbogImage image : images) {
            // DB에서 이미지 삭제
            abogImageRepository.delete(image);

            // 로컬 파일 삭제
            String filePath = UPLOAD_DIR + image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException e) {
                throw new RuntimeException("이미지 파일 삭제 중 오류가 발생했습니다: " + e.getMessage(), e);
            }
        }
    }

    private void validateImage(List<MultipartFile> images) {
        // 파일 개수 제한
        if (images.size() > MAX_IMAGES) {
            throw new BadRequestException("최대 " + MAX_IMAGES + "개의 이미지만 업로드 가능합니다.");
        }

        // 각 이미지 파일에 대해 추가 유효성 검사 수행
        for (MultipartFile image : images) {
            // 파일 크기 제한 (예: 5MB 이하)
            if (image.getSize() > MAX_FILE_SIZE) {
                throw new BadRequestException(
                        "이미지 파일 크기는 최대 5MB를 초과할 수 없습니다: " + image.getOriginalFilename());
            }

            // 파일 형식 제한 (예: JPG, JPEG, PNG)
            String contentType = image.getContentType();
            if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                throw new BadRequestException("허용되지 않는 파일 형식입니다: " + image.getOriginalFilename());
            }
        }
    }
}

