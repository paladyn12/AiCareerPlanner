package com.careerservice.ai_career_planner.domain.dto.test_result;

import com.careerservice.ai_career_planner.domain.dto.career_recommendation.CareerRecommendationDTO;
import com.careerservice.ai_career_planner.domain.entity.TestResult;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TestResultDTO {
    private Long id;
    private String interests;
    private String personalities;
    private String subjects;
    private String hollandCode;
    private List<CareerRecommendationDTO> recommendations;

    public static TestResultDTO from(TestResult testResult) {
        return TestResultDTO.builder()
                .id(testResult.getId())
                .interests(testResult.getInterests())
                .personalities(testResult.getPersonalities())
                .subjects(testResult.getSubjects())
                .hollandCode(testResult.getHollandCode())
                .recommendations(testResult.getRecommendations().stream()
                        .map(CareerRecommendationDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}