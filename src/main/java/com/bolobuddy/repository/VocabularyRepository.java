package com.bolobuddy.repository;

import com.bolobuddy.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByCategoryIgnoreCaseOrderByEnglishWordAsc(String category);

    @Query("select distinct v.category from Vocabulary v order by v.category asc")
    List<String> findDistinctCategories();
}
