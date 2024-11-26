package com.ssafy.aboha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 업로드 기본 디렉토리 (애플리케이션 실행 디렉토리 기준)
    private static final String UPLOAD_DIR = "file:uploads/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 단일 Resource Handler로 통합
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(UPLOAD_DIR)
                .setCachePeriod(0); // 개발 시 캐시 비활성화 (배포 시 적절히 설정)
    }
}
