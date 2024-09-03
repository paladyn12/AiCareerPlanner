package com.careerservice.ai_career_planner.domain.dto.test_result;

import com.careerservice.ai_career_planner.domain.entity.TestResult;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.domain.enum_class.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResultSaveDTO {
    private User user;
    private String interests;
    private String personalities;
    private String subjects;
    private String hollandCode;

    public TestResult toEntity() {
        return TestResult.builder()
                .user(user)
                .interests(interests)
                .personalities(personalities)
                .subjects(subjects)
                .hollandCode(hollandCode)
                .build();
    }
}
