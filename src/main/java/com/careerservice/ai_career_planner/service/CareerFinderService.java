package com.careerservice.ai_career_planner.service;

import com.careerservice.ai_career_planner.domain.entity.Career;
import com.careerservice.ai_career_planner.domain.entity.Survey;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.repository.CareerRepository;
import com.careerservice.ai_career_planner.repository.SurveyRepository;
import com.careerservice.ai_career_planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerFinderService {

    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final CareerRepository careerRepository;

    public Survey saveSurvey(Long userId, String interestsJson, String personalityJson) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Survey survey = new Survey(userId, user, interestsJson, personalityJson);
        return surveyRepository.save(survey);
    }

    public List<Career> recommendCareers(Long userId) {
        Survey survey = surveyRepository.findByUserId(userId);
        // Here you would implement your AI logic to analyze the survey and recommend careers
        // For now, we'll just return all careers
        return careerRepository.findAll();
    }
    // Add more methods as needed
}