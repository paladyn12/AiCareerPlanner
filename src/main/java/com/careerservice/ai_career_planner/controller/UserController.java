package com.careerservice.ai_career_planner.controller;

import com.careerservice.ai_career_planner.domain.dto.user.UserLoginRequest;
import com.careerservice.ai_career_planner.domain.dto.user.UserSignupRequest;
import com.careerservice.ai_career_planner.domain.entity.User;
import com.careerservice.ai_career_planner.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String signupUser(@Valid @ModelAttribute UserSignupRequest userSignupRequest, BindingResult bindingResult, Model model) {
        if (userService.signupValid(userSignupRequest, bindingResult).hasErrors()){
            return "users/signup";
        }
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

    @GetMapping("/profile")
    public String profilePage() {
        return "users/profile";
    }
}