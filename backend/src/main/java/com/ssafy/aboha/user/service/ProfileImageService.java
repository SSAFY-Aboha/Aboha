package com.ssafy.aboha.user.service;

import com.ssafy.aboha.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private static final Long MAX_FILE_SIZE = 10 * 1024 * 1024L; // 최대 10MB
    private static final String SUB_DIR = "user/"; // 하위 디렉토리
    private static final String UPLOAD_DIR = "uploads/" + SUB_DIR; // 저장 경로
    private static final String BASE_URL = "/uploads/" + SUB_DIR; // 반환할 URL 경로 prefix

    /**
     * 이미지 처리: 저장 + 기존 이미지 삭제
     */
    public String handleProfileImage(MultipartFile profileImage, String existingImageUrl) {
        try {
            // 이미지 유효성 검사
            validateImage(profileImage);

            // 기존 이미지 삭제
            if (existingImageUrl != null) {
                deleteExistingImage(existingImageUrl);
            }

            // 새 이미지 저장
            return saveImage(profileImage);
        } catch (IOException e) {
            throw new RuntimeException("이미지 처리 중 오류가 발생했습니다: " + e.getMessage(), e);
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
     * 기존 이미지 삭제
     */
    private void deleteExistingImage(String existingImageUrl) {
        String filePath = existingImageUrl.replace(BASE_URL, UPLOAD_DIR);
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("기존 이미지 삭제 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 이미지 유효성 검사
     */
    private void validateImage(MultipartFile profileImage) {
        if (profileImage.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("이미지 파일 크기는 최대 5MB를 초과할 수 없습니다: " + profileImage.getOriginalFilename());
        }

        String contentType = profileImage.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new BadRequestException("허용되지 않는 파일 형식입니다: " + profileImage.getOriginalFilename());
        }
    }
}
