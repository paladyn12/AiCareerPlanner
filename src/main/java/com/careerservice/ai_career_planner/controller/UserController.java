package com.careerservice.ai_career_planner.controller;

import com.careerservice.ai_career_planner.domain.dto.user.UserLoginRequest;
import com.careerservice.ai_career_planner.domain.dto.user.UserSignupRequest;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("userSignupRequest", new UserSignupRequest());
        return "users/signup";
    }
    @PostMapping("/signup")
    public String signupUser(@ModelAttribute UserSignupRequest userSignupRequest, Model model) {
        userService.createUser(userSignupRequest);
        model.addAttribute("message", "회원가입에 성공했습니다.\n로그인 페이지로 이동합니다.");
        model.addAttribute("nextUrl", "/users/login");
        return "printMessage";
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {

        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login") && !uri.contains("/signup")) {
            request.getSession().setAttribute("prevPage", uri);
        } //로그인, 회원가입 페이지가 아닌 페이지의 uri를 세션에 저장해놓고 로그인 후 돌아갈 수 있도록 함

        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "users/login";
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