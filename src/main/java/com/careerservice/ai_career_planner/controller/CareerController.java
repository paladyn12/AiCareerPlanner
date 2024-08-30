package com.careerservice.ai_career_planner.controller;

import com.careerservice.ai_career_planner.domain.entity.Career;
import com.careerservice.ai_career_planner.domain.entity.Survey;
import com.careerservice.ai_career_planner.service.CareerFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/careers")
public class CareerController {

    private final CareerFinderService careerFinderService;

    @GetMapping("/info")
    public String careerInfoPage() {
        return "careers/info";
    }

    //설문 페이지를 만들어 설문조사 후 userId와 설문 결과를 제출
    @PostMapping("/survey")
    public Survey submitSurvey(@RequestParam Long userId,
                               @RequestParam String interestsJson,
                               @RequestParam String personalityJson) {
        return careerFinderService.saveSurvey(userId, interestsJson, personalityJson);
    }

    @GetMapping("/recommend")
    public List<Career> getRecommendations(@RequestParam Long userId) {
        return careerFinderService.recommendCareers(userId);
    }
}
