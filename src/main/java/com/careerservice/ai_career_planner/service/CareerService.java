package com.careerservice.ai_career_planner.service;

import com.careerservice.ai_career_planner.domain.dto.test_result.TestResultSaveDTO;
import com.careerservice.ai_career_planner.domain.entity.CareerRecommendation;
import com.careerservice.ai_career_planner.domain.entity.TestResult;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.repository.TestResultRepository;
import com.careerservice.ai_career_planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CareerService {

    private final UserRepository userRepository;
    private final TestResultRepository testResultRepository;
    private final OpenAIService openAIService;

    @Transactional
    public void saveTestResult(String interests, String personalities, String subjects, String hollandCode) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginId = auth.getName();
        User user = userRepository.findByLoginId(loginId).get();

        Optional<TestResult> existingResult = testResultRepository.findByUserId(user.getId());
        TestResult result;

        if (existingResult.isPresent()) {
            result = existingResult.get();
            result.setHollandCode(hollandCode);
            result.setInterests(interests);
            result.setPersonalities(personalities);
            result.setSubjects(subjects);
            result.getRecommendations().clear();
            testResultRepository.save(result);
        } else {
            result = null;
            TestResultSaveDTO dto = new TestResultSaveDTO(user, interests, personalities, subjects, hollandCode);
            TestResult savedResult = testResultRepository.save(dto.toEntity());
            log.info("New result saved: {}", savedResult);
        }
        try {
            List<CareerRecommendation> recommendations = openAIService.generateCareerRecommendations(result);

            // 추천 결과 연결
            recommendations.forEach(rec -> {
                rec.setTestResult(result);
                result.getRecommendations().add(rec);
            });

            testResultRepository.save(result);
            log.info("Test result and recommendations saved for user: {}", loginId);
        } catch (Exception e) {
            log.error("Failed to generate recommendations", e);
            // 추천 생성 실패 시에도 테스트 결과는 저장
            testResultRepository.save(result);
            throw new RuntimeException("진로 추천 생성 중 오류가 발생했습니다.");
        }
    }
}