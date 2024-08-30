package com.careerservice.ai_career_planner.repository;

import com.careerservice.ai_career_planner.domain.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}