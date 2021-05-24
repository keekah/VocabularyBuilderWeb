package me.wingert.vocabularybuilder.VocabularyBuilder;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String firebaseId) {
        super("User not found with firebaseId " + firebaseId);
    }

    public UserNotFoundException(String firebaseId, Throwable cause) {
        super("Error retrieving Firebase UserRecord for user with firebaseId " + firebaseId, cause);
    }
}
