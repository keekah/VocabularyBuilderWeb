package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VocabWordRepository extends JpaRepository<VocabularyWord, Integer> {

    @Query(value = "SELECT * FROM vocabulary_words WHERE word = :word", nativeQuery = true)
    VocabularyWord findWord(String word);
}
