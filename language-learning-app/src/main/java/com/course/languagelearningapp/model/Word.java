package com.course.languagelearningapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String original;  // слово на иностранном (например, "dog")

    @Column(nullable = false)
    private String translation; // перевод (например, "собака")

    @Column(nullable = false)
    private String language; // язык (например, "english", "german")

    // Конструктор без параметров (обязательно для JPA)
    public Word() {}

    // Конструктор для удобства создания слов
    public Word(String original, String translation, String language) {
        this.original = original;
        this.translation = translation;
        this.language = language;
    }

    // Геттеры и сеттеры (обязательно)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}