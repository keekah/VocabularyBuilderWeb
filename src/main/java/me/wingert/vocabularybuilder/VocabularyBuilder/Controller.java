package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    private final VocabWordRepository repository;

    public Controller(VocabWordRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/vocabulary-words")
    public List<VocabularyWord> all()
    {
        return repository.findAll();
    }

    @PostMapping("/new-word")
    public VocabularyWord newWord(@RequestBody VocabularyWord newWord)
    {
        return repository.save(newWord);
    }

    @DeleteMapping("/vocabulary-words/{id}")
    public void deleteWord(@PathVariable int id)
    {
        repository.deleteById(id);
    }

    /*********************************************************************/
//    @GetMapping("/vocabulary-words/{id}")
//    public VocabularyWord getWord(@PathVariable int id)
//    {
//        return repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(id));
//    }
//
//
//
//    @PutMapping("/vocabulary-words/{id}")
//    public VocabularyWord updateDefinition(@PathVariable int id, @RequestBody VocabularyWord vocabWord)
//    {
//        return repository.findById(id)
//                .map(word -> {
//                    System.out.println("Word found! " + word);
//                    String existingDefinition = word.getDefinition();
//
//                    try {
//                        String definition = URLDecoder.decode(vocabWord.getDefinition(), StandardCharsets.UTF_8.name());
//                        System.out.println("existing definition: " + existingDefinition);
//                        if (existingDefinition == null)
//                            word.setDefinition(definition);
//                        else
//                            word.setDefinition(existingDefinition + "; " + definition);
//                    }
//                    catch (UnsupportedEncodingException e)
//                    {
//                        System.out.println("Error decoding definition");
//                        e.printStackTrace();
//                    }
//
//                    VocabularyWord updatedWord = repository.save(word);
//                    System.out.println("Saving new word " + updatedWord);
//                    return updatedWord;
//                })
//                .orElseThrow(() -> new ResourceNotFoundException(id));
//
//    }

}
