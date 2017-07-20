package com.mastong.quiz.creator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.mastong.quiz.creator.exception.UserAlreadyExistException;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // used to encrypt password before saving the user
    static final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(com.mastong.quiz.creator.domain.User newUser) throws UserAlreadyExistException {

        Assert.notNull(newUser, "newUser must not be null");

        com.mastong.quiz.creator.domain.User existingUser = findByLogin(newUser.getLogin());
        if (null != existingUser) {
            throw new UserAlreadyExistException(String.format("A user with the login '%s' already exist", newUser.getLogin()));
        }
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        newUser.setPasswordHash(encoder.encodePassword(newUser.getPasswordHash(), null));
        this.userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        com.mastong.quiz.creator.domain.User user = this.userRepository.findOne(login);
        if (null == user) {
            throw new UsernameNotFoundException("User details not found with this username: " + login);
        }

        String role = user.getRole();
        List<SimpleGrantedAuthority> authList = getAuthorities(role);
        System.out.println("Role in the user : " + role);
        for (SimpleGrantedAuthority sga : authList) {
            System.out.println("authority : " + sga);
        }
        return new User(login, user.getPasswordHash(), authList);
    }

    private List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        // you can also add different roles here
        // for example, the user is also an admin of the site, then you can add ROLE_ADMIN
        // so that he can view pages that are ROLE_ADMIN specific
        if (role != null && role.trim().length() > 0) {
            if (role.equals("admin")) {
                authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }
        return authList;
    }

    @Override
    public com.mastong.quiz.creator.domain.User findByLogin(String login) {
        return this.userRepository.findOne(login);
    }

}
