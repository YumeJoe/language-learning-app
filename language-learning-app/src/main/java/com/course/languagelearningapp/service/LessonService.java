package com.course.languagelearningapp.service;

import com.course.languagelearningapp.model.Word;
import com.course.languagelearningapp.repository.WordRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class LessonService {

    private final WordRepository wordRepository;
    private final Random random = new Random();

    public LessonService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word getRandomWord(String language) {
        List<Word> words = wordRepository.findByLanguage(language);
        if (words.isEmpty()) {
            return null; // или выбросить исключение
        }
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    public boolean checkAnswer(Long wordId, String userAnswer) {
        Word word = wordRepository.findById(wordId).orElse(null);
        if (word == null) {
            return false;
        }
        // Простейшая проверка: сравниваем перевод (можно улучшить)
        return word.getTranslation().equalsIgnoreCase(userAnswer.trim());
    }
}