package com.course.languagelearningapp.controller;

import com.course.languagelearningapp.model.Lesson;
import com.course.languagelearningapp.model.TestResult;
import com.course.languagelearningapp.model.User;
import com.course.languagelearningapp.model.Word;
import com.course.languagelearningapp.repository.LessonRepository;
import com.course.languagelearningapp.repository.TestResultRepository;
import com.course.languagelearningapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final TestResultRepository testResultRepository;

    public TestController(LessonRepository lessonRepository,
                          UserRepository userRepository,
                          TestResultRepository testResultRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.testResultRepository = testResultRepository;
    }

    @GetMapping("/start/{lessonId}")
    public String startTest(@PathVariable Long lessonId, HttpSession session) {
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if (lesson == null) {
            return "redirect:/lessons";
        }


        session.setAttribute("lessonId", lessonId);


        List<Word> words = lesson.getWords();
        session.setAttribute("testWords", words);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("correctAnswers", 0);
        session.setAttribute("lessonTitle", lesson.getTitle());

        return "redirect:/test/question";
    }

    @GetMapping("/question")
    public String showQuestion(HttpSession session, Model model) {
        List<Word> words = (List<Word>) session.getAttribute("testWords");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");

        if (words == null || currentIndex == null || currentIndex >= words.size()) {
            return "redirect:/test/result";
        }

        Word currentWord = words.get(currentIndex);
        model.addAttribute("word", currentWord);
        model.addAttribute("total", words.size());
        model.addAttribute("current", currentIndex + 1);
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("lessonTitle", session.getAttribute("lessonTitle"));

        return "test/question";
    }

    @PostMapping("/answer")
    public String checkAnswer(@RequestParam Long wordId,
                              @RequestParam String answer,
                              @RequestParam int currentIndex,
                              HttpSession session) {
        List<Word> words = (List<Word>) session.getAttribute("testWords");
        Integer correctAnswers = (Integer) session.getAttribute("correctAnswers");


        Word word = words.stream()
                .filter(w -> w.getId().equals(wordId))
                .findFirst()
                .orElse(null);

        if (word != null && word.getTranslation().equalsIgnoreCase(answer.trim())) {
            correctAnswers++;
            session.setAttribute("correctAnswers", correctAnswers);
        }


        session.setAttribute("currentIndex", currentIndex + 1);

        return "redirect:/test/question";
    }

    @GetMapping("/result")
    public String showResult(HttpSession session, Model model) {
        Integer correctAnswers = (Integer) session.getAttribute("correctAnswers");
        List<Word> words = (List<Word>) session.getAttribute("testWords");
        String lessonTitle = (String) session.getAttribute("lessonTitle");
        Long lessonId = (Long) session.getAttribute("lessonId");
        Long userId = (Long) session.getAttribute("userId");

        int total = words != null ? words.size() : 0;


        if (userId != null && lessonId != null) {
            User user = userRepository.findById(userId).orElse(null);
            Lesson lesson = lessonRepository.findById(lessonId).orElse(null);

            if (user != null && lesson != null) {
                TestResult result = new TestResult(user, lesson, correctAnswers, total);
                testResultRepository.save(result);
                System.out.println("✅ Результат сохранён для пользователя: " + user.getUsername());
            }
        }

        model.addAttribute("correct", correctAnswers);
        model.addAttribute("total", total);
        model.addAttribute("lessonTitle", lessonTitle);


        session.removeAttribute("testWords");
        session.removeAttribute("currentIndex");
        session.removeAttribute("correctAnswers");
        session.removeAttribute("lessonTitle");
        session.removeAttribute("lessonId");

        return "test/result";
    }
}