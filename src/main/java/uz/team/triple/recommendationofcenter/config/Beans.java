package uz.team.triple.recommendationofcenter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;

import java.time.Duration;

import static org.zalando.logbook.core.Conditions.*;
import static org.zalando.logbook.core.HeaderFilters.eachHeader;
import static org.zalando.logbook.core.QueryFilters.replaceQuery;

@Configuration
public class Beans {
    @Value("${telegram.bot.token}")
    private String botToken;

    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .condition(exclude(requestTo("/swagger-ui*/**"), contentType("application/octet-stream")))
                .queryFilter(replaceQuery("password", "<secret>"))
                .headerFilter(eachHeader((name, value) -> name.equalsIgnoreCase("Authorization") ? "XXX" : value))
                .headerFilter(eachHeader((name, value) -> name.equalsIgnoreCase("X-Secret") ? "XXX" : value))
                .build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(7))
                .setReadTimeout(Duration.ofSeconds(7))
                .build();
    }

    @Bean
    public RestClient restClient(RestTemplateBuilder restTemplateBuilder) {
        return RestClient.create(restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build());
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(botToken);
    }
}
