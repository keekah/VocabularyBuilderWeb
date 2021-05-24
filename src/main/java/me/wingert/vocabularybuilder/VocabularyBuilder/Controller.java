package me.wingert.vocabularybuilder.VocabularyBuilder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final String FIREBASE_ID = "FirebaseId";
    private static final String DEFINITION_DELIMITER = ";";

    private final VocabWordRepository vocabWordRepository;
    private final UserRepository userRepository;

    public Controller(VocabWordRepository vocabWordRepository, UserRepository userRepository)
    {
        this.vocabWordRepository = vocabWordRepository;
        this.userRepository = userRepository;
        logger.info("Controller :: Controller()");
    }

    @GetMapping("/vocabulary-words")
    public List<VocabularyWord> all(HttpServletRequest request)
    {
        logger.info("Controller :: all() ::");
        User user = updateUsersTable(request);

        List<VocabularyWord> list = vocabWordRepository.fetchWordsForUser(user.getId());
        logger.info("Controller :: all() :: number of words: " + list.size());
        return list;
    }

    // When retrieving all of a user's words, we first have to identify the user that made the request. Using the
    // Firebase UID obtained from the token in FirebaseAuthTokenFilter, check to see if this use is stored in our
    // "users" table; update their last_sign_in timestamp or add them to the table. We use "id" from this table to
    // match users to their vocabulary words. This method is called as soon as the user logs in.
    private User updateUsersTable(HttpServletRequest request) {
        // Verbose way to retrieve an attribute from a request. Later we use @RequestAttribute, which is a shorthand
        // way to perform this action.
        String firebaseId = (String) request.getAttribute(FIREBASE_ID);

        // Get UserRecord from Firebase so we can update "users" with that information.
        UserRecord userRecord;
        try {
            userRecord = FirebaseAuth.getInstance().getUser(firebaseId);
        } catch (FirebaseAuthException e) {
            logger.error("Unable to retrieve UserRecord from Firebase for user with firebase uid " + firebaseId);
            throw new UserNotFoundException(firebaseId, e);
        }

        User user = userRepository.findByFirebaseId(firebaseId);
        if (user == null) {
            user = new User(userRecord.getDisplayName(),
                    userRecord.getEmail(),
                    userRecord.isEmailVerified(),
                    userRecord.getUid(),
                    null,
                    new Timestamp(userRecord.getUserMetadata().getCreationTimestamp()),
                    new Timestamp(userRecord.getUserMetadata().getLastSignInTimestamp()));
            user = userRepository.save(user);
            logger.info("Controller :: all() :: Added user " + user.toString());
        }
        else {
            user.setLastSignInDateTime(new Timestamp(userRecord.getUserMetadata().getLastSignInTimestamp()));
            user = userRepository.save(user);
            logger.info("Controller :: all() :: Updated user " + user.toString());
        }
        return user;
    }


    @DeleteMapping("/vocabulary-words")
    public void deleteWord(String word, @RequestAttribute(FIREBASE_ID) String firebaseId)
    {
        logger.info("Controller :: deleteWord() ::");
        User user = lookupUser(firebaseId);

        VocabularyWord vocabWord = vocabWordRepository.findUserWord(word, user.getId());

        if (vocabWord == null) {
            logger.info("Controller :: deleteWord() :: User " + user.getDisplayName() + " has not stored this word.");
            throw new VocabWordNotFoundException(word);
        }

        logger.info("Controller :: deleteWord() :: Deleting word with id " + vocabWord.getId() + "...");
        vocabWordRepository.deleteById(vocabWord.getId());
    }

    // TODO Update word's added_date_time and modified_date_time
    @PutMapping("/vocabulary-words")
    public VocabularyWord updateVocabularyWord(@RequestBody VocabularyWord newWord, @RequestAttribute(FIREBASE_ID) String firebaseId)
    {
        User user = lookupUser(firebaseId);

        newWord.setUserId(user.getId());

        // Determine if this user has stored this word before.
        VocabularyWord existingWord = vocabWordRepository.findUserWord(newWord.getWord(), user.getId());

        // User is adding this word for the first time.
        if (existingWord == null) {
            return vocabWordRepository.save(newWord);
        }

        else {
            // If the user is adding this word again, but not passing a definition, do nothing.
            if (newWord.getDefinition() == null)
                return newWord;

            // If the user is adding this word again and providing a definition.
            return verifyNewDefinition(existingWord, newWord);
        }
    }

    // Verify that we are adding a new definition. existingWord may or may not have a definition (or multiple
    // definitions in one String, separated with ';'), newWord has a non-null definition.
    private VocabularyWord verifyNewDefinition(VocabularyWord existingWord, VocabularyWord newWord)
    {
        String existingDefinition = existingWord.getDefinition();
        String newDefinition = newWord.getDefinition();

        if (existingDefinition == null) {
            existingWord.setDefinition(newDefinition);
            return vocabWordRepository.save(existingWord);
        }

        String [] existingDefinitions = existingDefinition.split(DEFINITION_DELIMITER);

        for (String def : existingDefinitions)
            if (def.trim().equals(newDefinition))
                return existingWord;

        // Definition is unique.
        existingWord.setDefinition(existingDefinition + "; " + newDefinition);

        return vocabWordRepository.save(existingWord);
    }

    // Use the Firebase UID obtained from the token in FirebaseAuthTokenFilter to retrieve the user from the "users"
    // table. We need the user's id from this table to modify their list of words.
    private User lookupUser(String firebaseId) {

        if (firebaseId == null)
            throw new IllegalArgumentException("Uh oh! firebaseId should not be null");

        User user = userRepository.findByFirebaseId(firebaseId);

        if (user == null)
            throw new UserNotFoundException(firebaseId);

        return user;
    }

}
