package com.bolobuddy.repository;

import com.bolobuddy.model.ChildVocabulary;
import com.bolobuddy.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChildVocabularyRepository extends JpaRepository<ChildVocabulary, Long> {
    Optional<ChildVocabulary> findByChildIdAndVocabularyId(Long childId, Long vocabularyId);

    List<ChildVocabulary> findByChildIdAndIsMasteredTrueOrderByLastPracticedDesc(Long childId);

    List<ChildVocabulary> findByChildIdOrderByLastPracticedDesc(Long childId);

    List<ChildVocabulary> findByChildIdAndVocabularyIn(Long childId, List<Vocabulary> vocabulary);
}
