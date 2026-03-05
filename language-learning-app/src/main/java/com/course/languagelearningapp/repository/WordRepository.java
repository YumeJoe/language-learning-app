package com.course.languagelearningapp.repository;

import com.course.languagelearningapp.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    // Найти все слова по языку
    List<Word> findByLanguage(String language);

    // Найти случайное слово (сделаем позже через сервис, пока так)
}