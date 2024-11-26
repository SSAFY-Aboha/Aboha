package com.ssafy.aboha.user.service;

import com.ssafy.aboha.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private static final Long MAX_FILE_SIZE = 10 * 1024 * 1024L; // 최대 10MB
    private static final String SUB_DIR = "user/"; // 하위 디렉토리
    private static final String UPLOAD_DIR = "uploads/" + SUB_DIR; // 저장 경로
    private static final String BASE_URL = "/uploads/" + SUB_DIR; // 반환할 URL 경로 prefix

    /**
     * 이미지 파일 저장 및 URL 반환
     */
    public String saveImage(MultipartFile image) {
        try {
            // 이미지 유효성 검사
            validateImage(image);
            log.info("이미지 유효성 검사 완료.");

            // UUID 기반 고유 파일명 생성
            String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();

            // 실제 파일 저장 경로
            Path savePath = Paths.get(UPLOAD_DIR, fileName);

            // 디렉토리 생성 (없으면 생성)
            Files.createDirectories(savePath.getParent());

            // 파일 저장
            Files.write(savePath, image.getBytes());

            // 반환할 URL 경로 (DB에 저장할 경로)
            log.info("이미지 저장 완료: {}", BASE_URL + fileName);
            return BASE_URL + fileName;
        } catch (IOException e) {
            log.error("이미지 저장 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("이미지 저장 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 기존 이미지 삭제
     */
    public void deleteExistingImage(String existingImageUrl) {
        // 기존ImageUrl: "/uploads/user/fileName"
        String fileName = extractFileName(existingImageUrl);
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        try {
            Files.deleteIfExists(filePath);
            log.info("기존 이미지 파일 삭제 완료: {}", filePath.toString());
        } catch (IOException e) {
            log.error("기존 이미지 삭제 중 오류가 발생했습니다: {}", filePath.toString(), e);
            // 필요 시, 관리자에게 알리거나 재시도 로직을 수행할 수 있습니다.
        }
    }

    /**
     * 기존 이미지 파일명을 추출
     */
    private String extractFileName(String imageUrl) {
        // 쿼리 파라미터 제거
        int queryParamIndex = imageUrl.indexOf("?");
        if (queryParamIndex > 0) {
            imageUrl = imageUrl.substring(0, queryParamIndex);
        }
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    /**
     * 이미지 유효성 검사
     */
    private void validateImage(MultipartFile profileImage) {
        if (profileImage.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("이미지 파일 크기는 최대 10MB를 초과할 수 없습니다: " + profileImage.getOriginalFilename());
        }

        String contentType = profileImage.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new BadRequestException("허용되지 않는 파일 형식입니다: " + profileImage.getOriginalFilename());
        }
    }
}
