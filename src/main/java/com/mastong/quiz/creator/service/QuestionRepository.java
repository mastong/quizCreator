package com.mastong.quiz.creator.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mastong.quiz.creator.domain.Question;
import com.mastong.quiz.creator.domain.Quiz;
import com.mastong.quiz.creator.domain.User;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByQuizAndUser(Quiz quiz, User user);
}
