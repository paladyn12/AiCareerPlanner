package com.careerservice.ai_career_planner.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "portfolios")
@Getter
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String fileUrl;

}
