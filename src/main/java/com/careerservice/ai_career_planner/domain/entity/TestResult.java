package com.careerservice.ai_career_planner.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String hollandCode;

    @ElementCollection
    private List<String> interests;

    @ElementCollection
    private List<String> personalities;

}