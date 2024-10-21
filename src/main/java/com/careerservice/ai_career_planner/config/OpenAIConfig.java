package com.careerservice.ai_career_planner.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openai")
@Getter
@Setter
public class OpenAIConfig {
    @Value("${openai.api-key}")
    private String apiKey;
    private String model;
    private String apiUrl;
}
