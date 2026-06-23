package com.sakuraplanner.backend.config;

import io.openapi.models.Components;
import io.openapi.models.OpenAPI;
import io.openapi.models.info.Info;
import io.openapi.models.security.SecurityRequirement;
import io.openapi.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public OpenAPI customOpenAPI() {
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