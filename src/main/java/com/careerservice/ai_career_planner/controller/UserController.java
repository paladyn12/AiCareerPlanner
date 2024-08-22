package com.careerservice.ai_career_planner.controller;

import com.careerservice.ai_career_planner.domain.dto.user.UserSignupRequest;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("userSignupRequest", new UserSignupRequest());
        return "users/signup";
    }
    @PostMapping("/signup")
    public ResponseEntity<User> signupUser(@ModelAttribute UserSignupRequest userSignupRequest) {
        log.info("Post 요청");
        User createdUser = userService.createUser(userSignupRequest);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{loginId}")
    public ResponseEntity<User> getUserByLoginId(@PathVariable String loginId) {
        Optional<User> user = userService.getUserByLoginId(loginId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> user = userService.getUserByLoginId(userDetails.getLoginId());

        if (user.isPresent() && user.get().getId().equals(id)) {
            User updatedUser = userService.updateUser(userDetails);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}