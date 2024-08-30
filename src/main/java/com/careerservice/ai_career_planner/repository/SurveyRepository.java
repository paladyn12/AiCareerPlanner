package com.careerservice.ai_career_planner.repository;

import com.careerservice.ai_career_planner.domain.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Survey findByUserId(Long userId);
}
