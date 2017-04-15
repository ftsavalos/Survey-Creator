package com.models.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @ManyToOne
    @JsonIgnore
    private Survey survey;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private Set<Answer> answers;

    //
    //Constructors
    //

    public Question() {
    }

    public Question(String description, Survey survey) {
        this.description = description;
        this.survey = survey;
    }

    //
    //Accessors
    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    //
    //Overridden Methods
    //
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", survey=" + survey +
                '}';
    }
}
