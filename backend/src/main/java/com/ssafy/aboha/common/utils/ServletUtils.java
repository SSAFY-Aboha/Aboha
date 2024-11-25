package com.ssafy.aboha.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {

    private ServletUtils() {

    }

    /**
     * 현재 요청의 Servlet Context Path를 가져옴
     *
     * @return Context Path (없으면 빈 문자열 반환)
     */
    public static String getContextPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getContextPath();
        }
        return ""; // Context Path가 없으면 빈 문자열 반환
    }
}
