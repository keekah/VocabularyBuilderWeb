package me.wingert.vocabularybuilder.VocabularyBuilder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
