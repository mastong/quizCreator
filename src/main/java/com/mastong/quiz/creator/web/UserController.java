package com.mastong.quiz.creator.web;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mastong.quiz.creator.domain.User;
import com.mastong.quiz.creator.exception.UserAlreadyExistException;
import com.mastong.quiz.creator.service.UserService;

@Controller()
public class UserController {

    static Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    // GET
    @GetMapping("/")
    public String index() {
        return "redirect:quiz/list";
    }

    @GetMapping("/login")
    public String login() {
        log.info("Displaying /login");
        return "login";
    }

    @GetMapping("/newuser")
    public String newUser(User user, Model model) {
        return "newuser";
    }

    @PostMapping("/createuser")
    public String createUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("BindinResult error");
            return "newuser";
        }

        user.setRole("ROLE_USER");
        log.info("Full user " + user);
        try {
            userService.addUser(user);
        } catch (UserAlreadyExistException e) {
            log.error("", e);
            model.addAttribute("myErrors", e.getLocalizedMessage());
            log.error("Error adding user : user already exist");
            return "newuser";
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPasswordHash());
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        return "redirect:/";
    }
}
