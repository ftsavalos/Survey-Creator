package com.models.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.models.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SURVEY")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "survey")
    @JsonIgnore
    private Set<Question> questions;

    @ManyToOne
    @JsonIgnore
    private User user;

    //
    //Constructors
    //
    public Survey(Set<Question> questions, User user) {
        this.questions = questions;
        this.user = user;
    }

    public Survey() {
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

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //
    //Overridden Methods
    //
    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
//                ", questions=" + questions +
                ", user=" + user +
                '}';
    }
}
