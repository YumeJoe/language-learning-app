package com.course.languagelearningapp.controller;

import com.course.languagelearningapp.model.Word;
import com.course.languagelearningapp.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public String showLesson(Model model) {
        // Пока захардкодим язык, потом сделаем выбор
        Word word = lessonService.getRandomWord("english");
        model.addAttribute("word", word);
        return "lesson";
    }

    @PostMapping("/check")
    public String checkAnswer(@RequestParam Long wordId,
                              @RequestParam String answer,
                              Model model) {
        boolean isCorrect = lessonService.checkAnswer(wordId, answer);

        // Получаем слово снова, чтобы показать на странице
        Word word = lessonService.getRandomWord("english");
        model.addAttribute("word", word);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("message", isCorrect ? "Правильно! Молодец!" : "Неправильно, попробуй ещё раз.");

        return "lesson";
    }
}