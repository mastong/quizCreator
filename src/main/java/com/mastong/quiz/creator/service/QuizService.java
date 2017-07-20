package com.mastong.quiz.creator.service;

import java.io.File;
import java.util.List;

import com.mastong.quiz.creator.domain.Quiz;

public interface QuizService {

    List<Quiz> list();

    void add(Quiz quiz);

    Quiz findByName(String name);

    File export(String name);
}
