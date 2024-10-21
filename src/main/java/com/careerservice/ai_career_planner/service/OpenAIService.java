package com.careerservice.ai_career_planner.service;

import com.careerservice.ai_career_planner.config.OpenAIConfig;
import com.careerservice.ai_career_planner.domain.entity.CareerRecommendation;
import com.careerservice.ai_career_planner.domain.entity.TestResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIService {

    private final OpenAIConfig openAIConfig;
    private final RestTemplate restTemplate;

    public List<CareerRecommendation> generateCareerRecommendations(TestResult testResult) {
        String prompt = generatePrompt(testResult);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAIConfig.getApiKey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openAIConfig.getModel());
        requestBody.put("messages", Arrays.asList(
                Map.of("role", "system", "content", "당신은 진로 상담 전문가입니다. 학생의 특성을 바탕으로 적합한 진로를 추천해주세요."),
                Map.of("role", "user", "content", prompt)
        ));
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    openAIConfig.getApiUrl(),
                    request,
                    Map.class
            );

            return parseOpenAIResponse(response.getBody());
        } catch (Exception e) {
            log.error("OpenAI API 호출 중 오류 발생", e);
            throw new RuntimeException("진로 추천 생성 중 오류가 발생했습니다.");
        }
    }

    private String generatePrompt(TestResult testResult) {
        return String.format("""
            다음 학생의 특성을 바탕으로 3가지 진로를 추천해주세요:
            
            관심사: %s
            성격 특성: %s
            선호 과목: %s
            홀랜드 유형: %s
            
            다음 JSON 형식으로 응답해주세요:
            {
                "recommendations": [
                    {
                        "title": "진로명",
                        "description": "진로 설명 (100자 이내)",
                        "matchingReason": "추천 이유 (200자 이내)"
                    }
                ]
            }
            """,
                testResult.getInterests(),
                testResult.getPersonalities(),
                testResult.getSubjects(),
                testResult.getHollandCode()
        );
    }

    private List<CareerRecommendation> parseOpenAIResponse(Map response) {
        // OpenAI 응답에서 JSON 추출 및 파싱
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        String content = message.get("content").toString();ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, List<CareerRecommendation>> recommendations =
                    mapper.readValue(content, new TypeReference<>() {});
            return recommendations.get("recommendations");
        } catch (JsonProcessingException e) {
            log.error("응답 파싱 중 오류 발생", e);
            throw new RuntimeException("진로 추천 결과 처리 중 오류가 발생했습니다.");
        }
    }
}
