package com.careerservice.ai_career_planner.domain.dto.user;

import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.domain.enum_class.UserRole;
import lombok.Data;

@Data
public class UserSignupRequest {
    private String loginId;
    private String password;
    private String passwordCheck;
    private String email;
    private String name;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .email(email)
                .name(name)
                .userRole(UserRole.USER)
                .build();
    }
}
