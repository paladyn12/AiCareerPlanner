package com.careerservice.ai_career_planner.repository;

import com.careerservice.ai_career_planner.domain.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    Optional<TestResult> findByUserId(Long userId);

}
