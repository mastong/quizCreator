package com.mastong.quiz.creator.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.annotations.Expose;

@Entity
public class Quiz {

    @Expose
    @Id
    private String name;

    @Expose
    @Column()
    private String description;

    @Expose
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDescription();
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
