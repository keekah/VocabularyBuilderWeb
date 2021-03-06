package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE firebase_id = :firebaseId", nativeQuery = true)
    User findByFirebaseId(String firebaseId);
}
