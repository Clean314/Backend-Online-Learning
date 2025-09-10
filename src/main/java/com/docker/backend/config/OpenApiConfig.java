package com.docker.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", // name
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // HTTP 인증
                                        .scheme("bearer")              // Bearer 사용
                                        .bearerFormat("JWT")           // JWT 명시
                                        .in(SecurityScheme.In.HEADER)  // 헤더에 담기
                                        .name("Authorization")         // 헤더 키 이름
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                        .title("EduOn Server API")
                        .version("v1.0")
                        .description("온라인 평생학습 관리 웹 서비스로, JWT 인증이 필요한 API 문서입니다."));
    }
}