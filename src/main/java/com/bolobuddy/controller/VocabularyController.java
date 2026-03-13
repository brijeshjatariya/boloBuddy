package com.bolobuddy.controller;

import com.bolobuddy.model.ChildVocabulary;
import com.bolobuddy.model.Vocabulary;
import com.bolobuddy.service.VocabularyService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {

    private final VocabularyService vocabularyService;

    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return vocabularyService.getCategories();
    }

    @GetMapping("/category/{category}")
    public List<Vocabulary> getByCategory(@PathVariable String category) {
        return vocabularyService.getByCategory(category);
    }

    @GetMapping("/lesson/{childId}")
    public List<Vocabulary> getLesson(@PathVariable Long childId) {
        return vocabularyService.getNextLessonWords(childId);
    }

    @PostMapping("/practice")
    public ChildVocabulary practice(@RequestBody PracticeRequest request) {
        return vocabularyService.logPractice(request.childId(), request.wordId(), request.wasCorrect());
    }

    @GetMapping("/mastered/{childId}")
    public List<Vocabulary> getMastered(@PathVariable Long childId) {
        return vocabularyService.getMasteredWords(childId);
    }

    public record PracticeRequest(@NotNull Long childId, @NotNull Long wordId, boolean wasCorrect) {
    }
}
