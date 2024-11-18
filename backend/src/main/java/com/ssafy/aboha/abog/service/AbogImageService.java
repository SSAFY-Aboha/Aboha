package com.ssafy.aboha.abog.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.AbogImage;
import com.ssafy.aboha.abog.repository.AbogImageRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbogImageService {

    private final AbogImageRepository abogImageRepository;

    /**
     * 아보그 이미지 등록
     */
    public void uploadImages(Abog abog, List<MultipartFile> images) {
        try {
            // 이미지 파일 저장을 위한 경로 설정
            String uploadsDir = "src/main/resources/uploads/images/";

            // 각 이미지 파일에 대해 업로드 및 DB 저장 수행
            for(MultipartFile image : images) {
                // 이미지 파일 경로 저장
                String dbFilePath = saveImage(image, uploadsDir);

                // 엔티티 생성 및 저장
                AbogImage abogImage = AbogImage.builder()
                    .abog(abog)
                    .imageUrl(dbFilePath)
                    .build();

                abogImageRepository.save(abogImage);
            }
        } catch (IOException e) {
            // TODO: INTERVAL_SERVER_ERROR("이미지 업로드 중 오류가 발생했습니다")
            // 파일 저장 중 오류가 발생한 경우 처리
            e.printStackTrace();
        }

    }

    // 이미지 파일을 저장
    private String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        // 실제 파일이 저장될 경로
        String filePath = uploadsDir + fileName;
        // DB에 저장할 경로 문자열
        String dbFilePath = "/uploads/thumbnails/" + fileName;

        Path path = Paths.get(filePath); // Path 객체 생성
        Files.createDirectories(path.getParent()); // 디렉토리 생성
        Files.write(path, image.getBytes()); // 디렉토리에 파일 저장

        return dbFilePath;
    }

}
