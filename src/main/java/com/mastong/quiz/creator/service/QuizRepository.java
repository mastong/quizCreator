package com.mastong.quiz.creator.service;

import org.springframework.data.repository.CrudRepository;

import com.mastong.quiz.creator.domain.Quiz;

public interface QuizRepository extends CrudRepository<Quiz, String> {

}
