package com.mastong.quiz.creator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.google.gson.annotations.Expose;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Expose
    @NotNull
    @Pattern(regexp = "\\S+", message = "Can't be empty")
    @Column(nullable = false)
    private String statement;

    @Expose
    @Column()
    private String statementImg;

    @Expose
    @Column()
    private String statementAudio;

    @Expose
    @NotNull
    @Pattern(regexp = "\\S+", message = "Can't be empty")
    @Column(nullable = false)
    private String answer;

    @Expose
    @Column()
    private String answerImg;

    @Expose
    @Column()
    private String answerAudio;

    @Expose
    @NotNull(message = "Can't be empty")
    @Min(value = 1, message = "Must be between 1 and 5")
    @Max(value = 5, message = "Must be between 1 and 5")
    @Column(nullable = false)
    private Integer point;

    @ManyToOne
    @JoinColumn(name = "name")
    private Quiz quiz;

    @Expose
    @ManyToOne
    @JoinColumn(name = "login")
    private User user;

    @Override
    public String toString() {
        return getId() + "," + getStatement() + "," + getStatementImg() + "," + getStatementAudio() + "," + getAnswer() + "," + getAnswerImg() + ","
                + getAnswerAudio() + "," + getPoint() + "," + getQuiz();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getStatementImg() {
        return statementImg;
    }

    public void setStatementImg(String statementImg) {
        this.statementImg = statementImg;
    }

    public String getStatementAudio() {
        return statementAudio;
    }

    public void setStatementAudio(String statementAudio) {
        this.statementAudio = statementAudio;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerImg() {
        return answerImg;
    }

    public void setAnswerImg(String answerImg) {
        this.answerImg = answerImg;
    }

    public String getAnswerAudio() {
        return answerAudio;
    }

    public void setAnswerAudio(String answerAudio) {
        this.answerAudio = answerAudio;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
