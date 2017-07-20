package com.mastong.quiz.creator.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mastong.quiz.creator.domain.Quiz;

@Component("quizService")
@Transactional
public class QuizServiceImpl implements QuizService {

    static Logger log = Logger.getLogger(QuizServiceImpl.class.getName());

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<Quiz> list() {
        List<Quiz> quizzes = (List<Quiz>) this.quizRepository.findAll();

        return quizzes;
    }

    @Override
    public void add(Quiz quiz) {
        throw new UnsupportedOperationException("Not yet");

    }

    @Override
    public Quiz findByName(String name) {
        return this.quizRepository.findOne(name);
    }

    @Override
    public File export(String name) {
        Quiz quiz = this.findByName(name);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();// new Gson();
        String jsonInString = gson.toJson(quiz);
        log.info(quiz.getDescription());
        log.info("Quiz " + name + " to json : " + jsonInString);
        Path path = Paths.get(name.replace(' ', '_') + ".json");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(jsonInString);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return path.toFile();
    }

}
