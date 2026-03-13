package com.bolobuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "child_vocabulary")
public class ChildVocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", nullable = false)
    private Vocabulary vocabulary;

    @Column(nullable = false)
    private Integer attempts = 0;

    @Column(name = "correct_count", nullable = false)
    private Integer correctCount = 0;

    @Column(name = "accuracy_percent", nullable = false)
    private Integer accuracyPercent = 0;

    @Column(name = "is_mastered", nullable = false)
    private Boolean isMastered = false;

    @Column(name = "last_practiced", nullable = false)
    private LocalDateTime lastPracticed;

    @PrePersist
    public void prePersist() {
        if (lastPracticed == null) {
            lastPracticed = LocalDateTime.now();
        }
    }

    public void updateAccuracy() {
        if (attempts == null || attempts == 0) {
            accuracyPercent = 0;
            return;
        }
        accuracyPercent = Math.round((correctCount * 100.0f) / attempts);
        isMastered = attempts >= 3 && accuracyPercent >= 80;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Integer getAccuracyPercent() {
        return accuracyPercent;
    }

    public void setAccuracyPercent(Integer accuracyPercent) {
        this.accuracyPercent = accuracyPercent;
    }

    public Boolean getIsMastered() {
        return isMastered;
    }

    public void setIsMastered(Boolean mastered) {
        isMastered = mastered;
    }

    public LocalDateTime getLastPracticed() {
        return lastPracticed;
    }

    public void setLastPracticed(LocalDateTime lastPracticed) {
        this.lastPracticed = lastPracticed;
    }
}
