package uz.team.triple.recommendationofcenter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
@EnableAsync
public class RecommendationOfCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommendationOfCenterApplication.class, args);
    }

}
