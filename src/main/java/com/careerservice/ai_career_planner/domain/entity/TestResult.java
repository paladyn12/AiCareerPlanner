package com.careerservice.ai_career_planner.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String interests;
    private String personalities;
    private String subjects;
    private String hollandCode;

    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareerRecommendation> recommendations = new ArrayList<>();
}