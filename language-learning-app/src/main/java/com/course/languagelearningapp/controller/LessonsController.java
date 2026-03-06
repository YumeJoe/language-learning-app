package com.course.languagelearningapp.controller;

import com.course.languagelearningapp.model.Lesson;
import com.course.languagelearningapp.repository.LessonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository lessonRepository;

    public LessonsController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @GetMapping
    public String listLessons(Model model) {
        List<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("lessons", lessons);
        return "lessons/list";
    }

    @GetMapping("/{id}")
    public String viewLesson(@PathVariable Long id, Model model) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        model.addAttribute("lesson", lesson);
        return "lessons/view";
    }
}