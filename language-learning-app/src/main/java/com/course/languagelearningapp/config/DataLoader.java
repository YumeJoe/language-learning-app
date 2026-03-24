package com.course.languagelearningapp.config;

import com.course.languagelearningapp.model.Lesson;
import com.course.languagelearningapp.model.Word;
import com.course.languagelearningapp.repository.LessonRepository;
import com.course.languagelearningapp.repository.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final LessonRepository lessonRepository;
    private final WordRepository wordRepository;

    public DataLoader(LessonRepository lessonRepository, WordRepository wordRepository) {
        this.lessonRepository = lessonRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (lessonRepository.count() == 0) {
            System.out.println("Создаём тестовые уроки и слова");


            Lesson animalsLesson = new Lesson("Animals", "Basic animals vocabulary", "english");
            lessonRepository.save(animalsLesson);

            Word dog = new Word("dog", "собака", "english");
            dog.setLesson(animalsLesson);
            wordRepository.save(dog);

            Word cat = new Word("cat", "кот", "english");
            cat.setLesson(animalsLesson);
            wordRepository.save(cat);

            Word cow = new Word("cow", "корова", "english");
            cow.setLesson(animalsLesson);
            wordRepository.save(cow);


            Lesson foodLesson = new Lesson("Food", "Common food items", "english");
            lessonRepository.save(foodLesson);

            Word bread = new Word("bread", "хлеб", "english");
            bread.setLesson(foodLesson);
            wordRepository.save(bread);

            Word milk = new Word("milk", "молоко", "english");
            milk.setLesson(foodLesson);
            wordRepository.save(milk);

            Word apple = new Word("apple", "яблоко", "english");
            apple.setLesson(foodLesson);
            wordRepository.save(apple);

            System.out.println("Готово:");
            System.out.println("Урок 'Animals' с 3 словами");
            System.out.println("Урок 'Food' с 3 словами");
        } else {
            System.out.println("Уроки уже есть, пропускаем загрузку");
        }
    }
}