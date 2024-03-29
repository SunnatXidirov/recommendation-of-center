package uz.team.triple.recommendationofcenter.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.jetbrains.annotations.NotNull;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${information.application.base_url_server}")
    private String serverUrl;

    @Value("${information.application.base_url_local}")
    private String localUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    public OpenAPI springOpenAPIProd() {
        return new OpenAPI()
                .info(new Info()
                        .title("User service powered by RAC")
                        .description("REST API for The Recommendation of Center")
                        .version("1")
                        .contact(new Contact()
                                .name("Xidirov Sunnat")
                                .email("@gmail.com")
                                .url("https://github.com/SunnatXidirov"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))
                        .termsOfService("https://swagger.io/terms/"))
                .servers(List.of(
                        new Server().url(serverUrl).description("Production server"),
                        new Server().url(localUrl).description("Local server")
                )).addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components((new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                ));
    }

    @Bean
    public GroupedOpenApi allOpenApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/**")
                .build();
    }
}
