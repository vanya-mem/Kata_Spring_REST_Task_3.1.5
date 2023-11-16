package com.kata.spring.rest.kataspringrest.util;

import com.kata.spring.rest.kataspringrest.entities.User;
import com.kata.spring.rest.kataspringrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    User user = (User) target;
    try {
        userService.loadUserByUsername(user.getUsername());
    } catch (UsernameNotFoundException e) {
        return;
    }
    errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}
