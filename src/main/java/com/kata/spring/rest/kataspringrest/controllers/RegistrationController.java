package com.kata.spring.rest.kataspringrest.controllers;

import com.kata.spring.rest.kataspringrest.entities.User;
import com.kata.spring.rest.kataspringrest.repositories.RoleRepository;
import com.kata.spring.rest.kataspringrest.services.UserService;
import com.kata.spring.rest.kataspringrest.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final UserValidator userValidator;

    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator, RoleRepository roleRepository) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("userForm") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "/registration";

        userService.saveUser(user);

        return "redirect:/user";
    }
}