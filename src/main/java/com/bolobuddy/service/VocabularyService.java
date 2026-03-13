package com.bolobuddy.service;

import com.bolobuddy.model.Child;
import com.bolobuddy.model.ChildVocabulary;
import com.bolobuddy.model.Vocabulary;
import com.bolobuddy.repository.ChildRepository;
import com.bolobuddy.repository.ChildVocabularyRepository;
import com.bolobuddy.repository.VocabularyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final ChildRepository childRepository;
    private final ChildVocabularyRepository childVocabularyRepository;

    public VocabularyService(VocabularyRepository vocabularyRepository,
                             ChildRepository childRepository,
                             ChildVocabularyRepository childVocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
        this.childRepository = childRepository;
        this.childVocabularyRepository = childVocabularyRepository;
    }

    public List<String> getCategories() {
        return vocabularyRepository.findDistinctCategories();
    }

    public List<Vocabulary> getByCategory(String category) {
        return vocabularyRepository.findByCategoryIgnoreCaseOrderByEnglishWordAsc(category);
    }

    public List<Vocabulary> getNextLessonWords(Long childId) {
        childRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Child not found with id: " + childId));

        List<Vocabulary> allWords = vocabularyRepository.findAll();
        List<ChildVocabulary> progress = childVocabularyRepository.findByChildIdOrderByLastPracticedDesc(childId);

        Set<Long> masteredWordIds = new HashSet<>();
        for (ChildVocabulary item : progress) {
            if (Boolean.TRUE.equals(item.getIsMastered())) {
                masteredWordIds.add(item.getVocabulary().getId());
            }
        }

        List<Vocabulary> lessonPool = new ArrayList<>();
        for (Vocabulary word : allWords) {
            if (!masteredWordIds.contains(word.getId())) {
                lessonPool.add(word);
            }
        }

        lessonPool.sort(Comparator
                .comparing(Vocabulary::getDifficultyLevel)
                .thenComparing(Vocabulary::getEnglishWord));

        if (lessonPool.size() > 5) {
            return lessonPool.subList(0, 5);
        }
        return lessonPool;
    }

    @Transactional
    public ChildVocabulary logPractice(Long childId, Long wordId, boolean wasCorrect) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Child not found with id: " + childId));
        Vocabulary word = vocabularyRepository.findById(wordId)
                .orElseThrow(() -> new IllegalArgumentException("Vocabulary word not found with id: " + wordId));

        ChildVocabulary progress = childVocabularyRepository.findByChildIdAndVocabularyId(childId, wordId)
                .orElseGet(() -> {
                    ChildVocabulary created = new ChildVocabulary();
                    created.setChild(child);
                    created.setVocabulary(word);
                    created.setAttempts(0);
                    created.setCorrectCount(0);
                    created.setAccuracyPercent(0);
                    created.setIsMastered(false);
                    return created;
                });

        progress.setAttempts(progress.getAttempts() + 1);
        if (wasCorrect) {
            progress.setCorrectCount(progress.getCorrectCount() + 1);
        }
        progress.setLastPracticed(LocalDateTime.now());
        progress.updateAccuracy();

        return childVocabularyRepository.save(progress);
    }

    public List<Vocabulary> getMasteredWords(Long childId) {
        List<ChildVocabulary> mastered = childVocabularyRepository.findByChildIdAndIsMasteredTrueOrderByLastPracticedDesc(childId);
        List<Vocabulary> words = new ArrayList<>();
        for (ChildVocabulary childVocabulary : mastered) {
            words.add(childVocabulary.getVocabulary());
        }
        return words;
    }
}
