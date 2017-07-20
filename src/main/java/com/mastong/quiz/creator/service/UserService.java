package com.mastong.quiz.creator.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mastong.quiz.creator.domain.User;
import com.mastong.quiz.creator.exception.UserAlreadyExistException;

//TODO is this a good idea to have the userService also implement the Spring UserDetailService. Should-we separate the 2 class?
public interface UserService extends UserDetailsService {

    /**
     * Register a new user. Will throw an exception if the user already exists.
     * @param newUser
     */
    void addUser(User newUser) throws UserAlreadyExistException;

    User findByLogin(String login);
}
