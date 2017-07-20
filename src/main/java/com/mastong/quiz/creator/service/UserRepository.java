package com.mastong.quiz.creator.service;

import org.springframework.data.repository.CrudRepository;

import com.mastong.quiz.creator.domain.User;

public interface UserRepository extends CrudRepository<User, String> {

}
