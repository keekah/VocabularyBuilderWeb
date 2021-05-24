package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VocabWordRepository extends JpaRepository<VocabularyWord, Integer> {

    @Query(value = "SELECT * FROM vocabulary_words WHERE user_id = :id", nativeQuery = true)
    List<VocabularyWord> fetchWordsForUser(int id);

    @Query(value = "SELECT * FROM vocabulary_words WHERE word = :word AND user_id = :userId", nativeQuery = true)
    VocabularyWord findUserWord(String word, int userId);
}
