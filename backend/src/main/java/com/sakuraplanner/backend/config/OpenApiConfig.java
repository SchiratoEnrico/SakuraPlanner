package com.sakuraplanner.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration class to set up Swagger/OpenAPI documentation.
 * Configures global JWT authentication support for the Swagger UI interface.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Defines the OpenAPI bean definition with custom metadata and JWT security scheme.
     */
    @Bean
    OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                // 1. Add general metadata about your API
                .info(new Info()
                        .title("Sakura Planner API Documentation")
                        .version("1.0")
                        .description("RESTful API documentation for the Sakura Planner Japan itinerary builder application."))
                
                // 2. Globally apply the security requirement to all endpoints in Swagger UI
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                
                // 3. Define the structural security scheme (JWT Bearer Token)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Input your valid JWT Access Token to access secured endpoints.")));
    }
}