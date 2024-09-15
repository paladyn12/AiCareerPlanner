package com.careerservice.ai_career_planner.service;

import com.careerservice.ai_career_planner.domain.dto.test_result.TestResultSaveDTO;
import com.careerservice.ai_career_planner.domain.entity.TestResult;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.repository.TestResultRepository;
import com.careerservice.ai_career_planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CareerService {

    private final UserRepository userRepository;
    private final TestResultRepository testResultRepository;

    public void saveTestResult(String interests, String personalities, String subjects, String hollandCode) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginId = auth.getName();

        User user = userRepository.findByLoginId(loginId).get();

        Optional<TestResult> existingResult = testResultRepository.findByUserId(user.getId());

        if (existingResult.isPresent()) {
            TestResult result = existingResult.get();
            result.setHollandCode(hollandCode);
            result.setInterests(interests);
            result.setPersonalities(personalities);
            result.setSubjects(subjects);
            testResultRepository.save(result);
        } else {
            TestResultSaveDTO dto = new TestResultSaveDTO(user, interests, personalities, subjects, hollandCode);
            TestResult savedResult = testResultRepository.save(dto.toEntity());
            log.info("New result saved: {}", savedResult);
        }
    }
}