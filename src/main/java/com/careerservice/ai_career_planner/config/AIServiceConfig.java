package com.careerservice.ai_career_planner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AIServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Add more beans and configurations for AI service integration
}
