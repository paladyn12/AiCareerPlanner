package com.careerservice.ai_career_planner.domain.dto.career_recommendation;

import com.careerservice.ai_career_planner.domain.entity.CareerRecommendation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CareerRecommendationDTO {
    private String title;
    private String description;
    private String matchingReason;

    public static CareerRecommendationDTO from(CareerRecommendation recommendation) {
        return CareerRecommendationDTO.builder()
                .title(recommendation.getTitle())
                .description(recommendation.getDescription())
                .matchingReason(recommendation.getMatchingReason())
                .build();
    }
}