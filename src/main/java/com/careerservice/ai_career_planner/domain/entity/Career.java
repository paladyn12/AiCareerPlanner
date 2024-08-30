package com.careerservice.ai_career_planner.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "careers")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String requiredSkills;
    private String futureProspects;

}