package com.careerservice.ai_career_planner.repository;

import com.careerservice.ai_career_planner.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByName(String name);
    Boolean existsByLoginId(String loginId);
}
