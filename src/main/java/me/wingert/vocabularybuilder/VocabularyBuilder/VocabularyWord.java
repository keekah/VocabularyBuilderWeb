package me.wingert.vocabularybuilder.VocabularyBuilder;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "vocabulary_words")
public class VocabularyWord {

    @Id
    @GeneratedValue
    private int id;

    private String word;

    private String definition;

    @Column(name = "user_id")
    private Integer userId;

    // TODO Update constructor, getters and setter for these fields.
    @Column(name = "added_date_time")
    private Timestamp addedDateTime;

    @Column(name = "modified_date_time")
    private Timestamp modifiedDateTime;


    public VocabularyWord() {}

    public void setId(int id) { this.id = id; }

    public void setWord(String word) { this.word = word; }

    public void setDefinition(String definition) { this.definition = definition; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public void setAddedDateTime(Timestamp timestamp) { addedDateTime = timestamp; }

    public void setModifiedDateTime(Timestamp timestamp) { modifiedDateTime = timestamp; }

    public int getId() { return id; }

    public String getWord() { return word; }

    public String getDefinition() { return definition; }

    public Integer getUserId() { return userId; }

    public Timestamp getAddedDateTime() { return addedDateTime; }

    public Timestamp getModifiedDateTime() { return modifiedDateTime; }

    @Override
    public String toString()
    {
        return id + ", " + word + ", " + definition + ", " + userId;
    }

}