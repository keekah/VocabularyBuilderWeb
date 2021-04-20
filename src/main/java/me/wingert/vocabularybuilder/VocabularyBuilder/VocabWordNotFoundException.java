package me.wingert.vocabularybuilder.VocabularyBuilder;

public class VocabWordNotFoundException extends RuntimeException {

    VocabWordNotFoundException(int id)
    {
        super("Vocab word not found with id " + id);
    }

    VocabWordNotFoundException(String word) { super ("Vocab word " + word + " not found"); }
}
