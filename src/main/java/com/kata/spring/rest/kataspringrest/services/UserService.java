package com.kata.spring.rest.kataspringrest.services;

import com.kata.spring.rest.kataspringrest.entities.Role;
import com.kata.spring.rest.kataspringrest.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findUserById(long id);

    boolean saveUser(User user);

    void deleteUserById(long id);


    void updateUser(User user);

    void saveRole(Role role);

    void saveUserRole(User user, Role role);

    List<Role> getAllRoles();

    List<User> getListOfUsers();
}
