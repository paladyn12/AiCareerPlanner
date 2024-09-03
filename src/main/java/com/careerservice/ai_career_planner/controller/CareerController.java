package com.careerservice.ai_career_planner.controller;

import com.careerservice.ai_career_planner.service.CareerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/career")
public class CareerController {

    private final CareerService careerService;

    @GetMapping("/step1")
    public String careerFinderStep1Page() {
        return "careers/careerFinderStep1";
    }

    @GetMapping("/step2/{hollandCode}")
    public String careerFinderStep2Page(@PathVariable String hollandCode, Model model) {
        model.addAttribute("hollandCode", hollandCode);
        return "careers/careerFinderStep2";
    }

    @PostMapping("/final")
    public String careerFinderFinal(@RequestParam("interests") String[] interests,
                                    @RequestParam("personalities") String[] personalities,
                                    @RequestParam("subjects") String[] subjects,
                                    @RequestParam("hollandCode") String hollandCode) {
        // 관심사, 성격, 과목, Holland 코드 출력 (예제용)
        String interest = String.join(", ", interests);
        String personality = String.join(", ", personalities);
        String subject = String.join(", ", subjects);
        System.out.println("Interests: " + String.join(", ", interests));
        System.out.println("Personalities: " + String.join(", ", personalities));
        System.out.println("Subjects: " + String.join(", ", subjects));
        System.out.println("Holland Code: " + hollandCode);

        careerService.saveTestResult(interest, personality, subject, hollandCode);

        return "redirect:/";
    }
}
