package com.careerservice.ai_career_planner.service;

import com.careerservice.ai_career_planner.domain.dto.test_result.TestResultDTO;
import com.careerservice.ai_career_planner.domain.dto.user.UserSignupRequest;
import com.careerservice.ai_career_planner.domain.entity.TestResult;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.repository.TestResultRepository;
import com.careerservice.ai_career_planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final TestResultRepository testResultRepository;
    private final BCryptPasswordEncoder encoder;

    public User createUser(UserSignupRequest req) {
        return userRepository.save(req.toEntity(encoder.encode(  req.getPassword()) ));
    }

    public BindingResult signupValid(UserSignupRequest req, BindingResult bindingResult) {
        if(req.getLoginId().isEmpty()) {
            bindingResult.addError(new FieldError("req", "loginId", "아이디가 비었습니다."));
        } else if (userRepository.existsByLoginId(req.getLoginId())) {
            bindingResult.addError(new FieldError("req", "loginId", "아이디가 중복됩니다."));
        }

        if (req.getPassword().isEmpty()) {
            bindingResult.addError(new FieldError("req", "password", "비밀번호가 비어있습니다."));
        } else if (!req.getPassword().equals(req.getPasswordCheck())) {
            bindingResult.addError(new FieldError("req", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if (req.getEmail().isEmpty()) {
            bindingResult.addError(new FieldError("req", "email", "이메일이 비어있습니다."));
        }

        if (req.getName().isEmpty()) {
            bindingResult.addError(new FieldError("req", "name", "닉네임이 비어있습니다."));
        } else if (req.getName().length()>10) {
            bindingResult.addError(new FieldError("req", "name", "닉네임이 10자를 넘습니다."));
        }

        return bindingResult;
    }

    public TestResultDTO getTestResultWithRecommendations(Long userId) {
        TestResult testResult = testResultRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Test result not found"));

        return TestResultDTO.from(testResult);
    }

}