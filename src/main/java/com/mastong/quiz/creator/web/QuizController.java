package com.mastong.quiz.creator.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mastong.quiz.creator.domain.Question;
import com.mastong.quiz.creator.domain.Quiz;
import com.mastong.quiz.creator.domain.User;
import com.mastong.quiz.creator.service.QuestionService;
import com.mastong.quiz.creator.service.QuizService;
import com.mastong.quiz.creator.service.StorageService;
import com.mastong.quiz.creator.service.UserService;

@Controller()
@RequestMapping("/quiz")
public class QuizController {
    static Logger log = Logger.getLogger(QuizController.class.getName());

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/list")
    public String index(Principal principal, Model model) {

        List<Quiz> quizzes = this.quizService.list();
        model.addAttribute("quizzes", quizzes);

        if (null != quizzes) {
            Quiz quiz = quizzes.get(0);
            User user = this.userService.findByLogin(principal.getName());
            List<Question> questions = questionService.findByQuizAndUser(quiz, user);
            log.info("Quiz affiché : " + quiz);
            log.info("Nb questions : " + questions.size());
            model.addAttribute("questions", questions);
        }

        return "quiz";
    }

    @GetMapping("/newquestion")
    public String newQuestion(Question question, String quizName, Model model) {
        if (!model.containsAttribute("quizName")) {
            model.addAttribute("quizName", quizName);
        }
        return "newquestion";
    }

    @PostMapping("/addquestion")
    public String addQuestion(@Valid Question question, MultipartFile statementImgFile, MultipartFile statementAudioFile, MultipartFile answerImgFile,
            MultipartFile answerAudioFile, BindingResult bindingResult, String quizName, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            model.addAttribute("quizName", quizName);
            return "newquestion";
        }

        boolean filesOK = true;
        if (storageService.exist(statementImgFile.getOriginalFilename())) {
            filesOK = false;
            bindingResult.addError(new FieldError("Question", "statementImg", "The statement image file already exist. Please rename it."));
            log.error("The statement image file already exist. Please rename it.");
        }
        if (storageService.exist(statementAudioFile.getOriginalFilename())) {
            filesOK = false;
            bindingResult.addError(new FieldError("Question", "statementAudio", "The statement audio file already exist. Please rename it."));
            log.error("The statement audio file already exist. Please rename it.");
        }
        if (storageService.exist(answerImgFile.getOriginalFilename())) {
            filesOK = false;
            bindingResult.addError(new FieldError("Question", "answerImg", "The answer image file already exist. Please rename it."));
            log.error("The answer image file already exist. Please rename it.");
        }
        if (storageService.exist(answerAudioFile.getOriginalFilename())) {
            filesOK = false;
            bindingResult.addError(new FieldError("Question", "answerAudio", "The answer audio file already exist. Please rename it."));
            log.error("The answer audio file already exist. Please rename it.");
        }

        if (!filesOK) {
            model.addAttribute("question", question);
            model.addAttribute("quizName", quizName);
            return "newquestion";
        }

        if (StringUtils.isNotEmpty(statementImgFile.getOriginalFilename())) {
            question.setStatementImg(statementImgFile.getOriginalFilename());
            storageService.store(statementImgFile);
        }
        if (StringUtils.isNotEmpty(statementAudioFile.getOriginalFilename())) {
            question.setStatementAudio(statementAudioFile.getOriginalFilename());
            storageService.store(statementAudioFile);
        }
        if (StringUtils.isNotEmpty(answerImgFile.getOriginalFilename())) {
            question.setAnswerImg(answerImgFile.getOriginalFilename());
            storageService.store(answerImgFile);
        }
        if (StringUtils.isNotEmpty(answerAudioFile.getOriginalFilename())) {
            question.setAnswerAudio(answerAudioFile.getOriginalFilename());
            storageService.store(answerAudioFile);
        }
        Quiz quiz = this.quizService.findByName(quizName);
        question.setQuiz(quiz);
        User user = this.userService.findByLogin(principal.getName());
        question.setUser(user);
        log.info("Question to save : " + question);

        this.questionService.add(question);
        return "redirect:/quiz/list";
    }

    @GetMapping("/editquestion")
    public String editQuestion(@RequestParam("questionId") String questionId, Model model) {
        Question question = this.questionService.findById(Long.valueOf(questionId));
        model.addAttribute("question", question);
        String quizName = question.getQuiz().getName();
        model.addAttribute("quizName", quizName);
        return "editquestion";
    }

    @PostMapping("/updatequestion")
    public String editQuestion(@Valid Question question, BindingResult bindingResult, String quizName, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("quizName", quizName);
            model.addAttribute("questionId", question.getId());
            return "editquestion";
        }
        Quiz quiz = this.quizService.findByName(quizName);
        question.setQuiz(quiz);
        User user = this.userService.findByLogin(principal.getName());
        question.setUser(user);
        this.questionService.add(question);
        return "redirect:/quiz/list";
    }

    @GetMapping("/deletequestion")
    public String deleteQuestion(String questionId) {
        this.questionService.delete(Long.valueOf(questionId));
        return "redirect:/quiz/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin(Model model) {
        List<Quiz> quizzes = this.quizService.list();
        model.addAttribute("quizzes", quizzes);
        return "admin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/export")
    public void exportQuiz(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String quizSelector = request.getParameter("quizSelector");
        File file = this.quizService.export(quizSelector);

        if (!file.exists()) {
            String errorMessage = "Sorry. The file you are looking for does not exist";
            log.error(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            log.info("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        log.info("mimetype : " + mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        response.setContentType(mimeType);
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
