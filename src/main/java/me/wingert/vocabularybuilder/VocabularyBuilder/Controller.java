package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final VocabWordRepository vocabWordRepository;
    private final UserRepository userRepository;

    public Controller(VocabWordRepository vocabWordRepository, UserRepository userRepository)
    {
        this.vocabWordRepository = vocabWordRepository;
        this.userRepository = userRepository;
        logger.info("Controller created");
    }

    @GetMapping("/vocabulary-words")
    public List<VocabularyWord> all()
    {
        logger.info("Controller:: all()");
        return vocabWordRepository.findAll();
    }

//    @GetMapping("/vocabulary-words")
//    public List<VocabularyWord> getUserVocabularyWords() {
//
//    }

    @PostMapping("/new-word")
    public VocabularyWord newWord(@RequestBody VocabularyWord newWord)
    {

        logger.info("Controller:: newWord()");
        return vocabWordRepository.save(newWord);
    }

    @DeleteMapping("/vocabulary-words")
    public void deleteWord(String word)
    {
        logger.info("Controller:: deleteWord()");
        VocabularyWord vocab = vocabWordRepository.findWord(word);

        if (vocab == null)
            throw new VocabWordNotFoundException(word);

        vocabWordRepository.deleteById(vocab.getId());
    }

    @PutMapping("/vocabulary-words")
    public VocabularyWord updateVocabularyWord(@RequestBody VocabularyWord vocabWord)
    {
        logger.info("Controller:: updateVocabularyWord()");

        VocabularyWord existingWord = vocabWordRepository.findWord(vocabWord.getWord());

        // If this is a new word, store it as-is.
        if (existingWord == null)
            return vocabWordRepository.save(vocabWord);

        // If we are adding this word again, but not passing a definition, do nothing.
        if (vocabWord.getDefinition() == null)
            return vocabWord;

        // If we are adding this word again and providing a definition.
        return verifyNewDefinition(existingWord, vocabWord);
    }

    // Verify that we are adding a new definition. existingWord may or may not have a definition (or multiple
    // definitions in one String, separated with ';'), newWord has a non-null definition.
    private VocabularyWord verifyNewDefinition(VocabularyWord existingWord, VocabularyWord newWord)
    {
        logger.info("Controller:: verifyNewDefinition()");

        String existingDefinition = existingWord.getDefinition();
        String newDefinition = newWord.getDefinition();

        if (existingDefinition == null)
        {
            existingWord.setDefinition(newDefinition);
            return vocabWordRepository.save(existingWord);
        }

        String [] existingDefinitions = existingDefinition.split(";");

        for (String def : existingDefinitions)
            if (def.trim().equals(newDefinition))
                return existingWord;

        // Adding a word that already exists and contains a new definition.
        existingWord.setDefinition(existingDefinition + "; " + newDefinition);

        return vocabWordRepository.save(existingWord);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user)
    {
        logger.info("Controller:: updateUser()");
        return null;
    }


}
