package com.mastong.quiz.creator.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mastong.quiz.creator.domain.Question;
import com.mastong.quiz.creator.domain.Quiz;
import com.mastong.quiz.creator.domain.User;

@Component("questionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void add(Question question) {
        // TODO Should validation the question before saving it
        this.questionRepository.save(question);
    }

    @Override
    public Question findById(Long id) {
        return this.questionRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        this.questionRepository.delete(id);
    }

    @Override
    public List<Question> findByQuizAndUser(Quiz quiz, User user) {
        return this.questionRepository.findByQuizAndUser(quiz, user);
    }
}
