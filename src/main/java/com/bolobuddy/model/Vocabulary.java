package com.bolobuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "vocabulary")
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "english_word", nullable = false, length = 100)
    private String englishWord;

    @NotBlank
    @Column(name = "hindi_word", nullable = false, length = 100)
    private String hindiWord;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String category;

    @Min(1)
    @Max(5)
    @Column(name = "difficulty_level", nullable = false)
    private Integer difficultyLevel = 1;

    @Column(length = 10)
    private String emoji;

    @Column(name = "example_sentence", length = 200)
    private String exampleSentence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getHindiWord() {
        return hindiWord;
    }

    public void setHindiWord(String hindiWord) {
        this.hindiWord = hindiWord;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }
}
