package com.course.languagelearningapp.controller;

import com.course.languagelearningapp.model.TestResult;
import com.course.languagelearningapp.model.User;
import com.course.languagelearningapp.repository.TestResultRepository;
import com.course.languagelearningapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final TestResultRepository testResultRepository;

    public ProfileController(UserRepository userRepository, TestResultRepository testResultRepository) {
        this.userRepository = userRepository;
        this.testResultRepository = testResultRepository;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        List<TestResult> results = testResultRepository.findByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("results", results);

        return "profile";
    }
}