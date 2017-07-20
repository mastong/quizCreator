package com.mastong.quiz.creator.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.gson.annotations.Expose;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    private String login;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getLogin() + "," + getPasswordHash() + ", " + getRole();
    }
}
