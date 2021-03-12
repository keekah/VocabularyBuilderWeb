package me.wingert.vocabularybuilder.VocabularyBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vocabulary_words")
public class VocabularyWord {

    @Id
    @GeneratedValue
    private int id;
    private String word;
    private String definition;

    public VocabularyWord() {}

    public VocabularyWord(String word, String definition)
    {
        this.word = word;
        this.definition = definition;
    }

    public void setId(int id) { this.id = id; }

    public void setWord(String word) { this.word = word; }

    public void setDefinition(String definition) { this.definition = definition; }

    public int getId() { return id; }

    public String getWord() { return word; }

    public String getDefinition() { return definition; }

    @Override
    public String toString()
    {
        return id + ", " + word + ": " + definition;
    }

}