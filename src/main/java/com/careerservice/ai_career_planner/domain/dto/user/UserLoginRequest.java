package com.careerservice.ai_career_planner.domain.dto.user;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String loginId;
    private String password;
}
