package com.careerservice.ai_career_planner.service;

import com.careerservice.ai_career_planner.domain.dto.user.UserSignupRequest;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public User createUser(UserSignupRequest req) {
        return userRepository.save(req.toEntity(encoder.encode(  req.getPassword()) ));
    }

    public Optional<User> getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}