package com.ssafy.aboha.user.service;

import com.ssafy.aboha.common.exception.BadRequestException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private static final Long MAX_FILE_SIZE = 5 * 1024 * 1024L; // 최대 5MB
    private static final String BASE_URL = "/uploads/user/"; // 반환할 URL prefix
    private static final String UPLOAD_DIR = "src/main/resources/uploads/user/"; // 저장 경로

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
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        Path savePath = Paths.get(UPLOAD_DIR, fileName);
        Files.createDirectories(savePath.getParent());
        Files.write(savePath, image.getBytes());
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
