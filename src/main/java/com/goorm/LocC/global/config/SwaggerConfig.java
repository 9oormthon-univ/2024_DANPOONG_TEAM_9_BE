package com.goorm.LocC.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "LocC API",
                version = "v1.0.0",
                description = "LocC: Local Creator API 명세서입니다."
        )
)
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "JWT";

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(SECURITY_SCHEME_NAME);
        Components components = new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat(SECURITY_SCHEME_NAME));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}