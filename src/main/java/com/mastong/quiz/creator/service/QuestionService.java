package com.mastong.quiz.creator.service;

import java.util.List;

import com.mastong.quiz.creator.domain.Question;
import com.mastong.quiz.creator.domain.Quiz;
import com.mastong.quiz.creator.domain.User;

public interface QuestionService {

    void add(Question question);

    Question findById(Long id);

    List<Question> findByQuizAndUser(Quiz quiz, User user);

    void delete(Long id);
}
