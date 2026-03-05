package com.course.languagelearningapp.config;

import com.course.languagelearningapp.model.Word;
import com.course.languagelearningapp.repository.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final WordRepository wordRepository;

    public DataLoader(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверим, есть ли уже слова
        if (wordRepository.count() == 0) {
            // Добавляем слова
            wordRepository.save(new Word("dog", "собака", "english"));
            wordRepository.save(new Word("cat", "кот", "english"));
            wordRepository.save(new Word("house", "дом", "english"));
            wordRepository.save(new Word("car", "машина", "english"));
            wordRepository.save(new Word("book", "книга", "english"));

            System.out.println("Добавлены тестовые слова");
        }
    }
}