package me.wingert.vocabularybuilder.VocabularyBuilder;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("User not found with id " + id);
    }
}
