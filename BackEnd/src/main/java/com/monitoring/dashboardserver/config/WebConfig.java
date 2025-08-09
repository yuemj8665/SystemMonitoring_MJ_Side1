package com.monitoring.dashboardserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
        .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173") // 프론트엔드 주소들
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP메소드
        .allowedHeaders("*") // 모든 헤더 정보 허용
        .allowCredentials(true) // 쿠키와 같은 자격 증명 정보 허용
        .maxAge(3600); // preflight 요청의 캐시 시간 (1시간)
    }
    
}
