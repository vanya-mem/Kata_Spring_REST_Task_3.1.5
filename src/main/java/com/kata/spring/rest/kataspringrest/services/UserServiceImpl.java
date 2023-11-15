package com.kata.spring.rest.kataspringrest.services;

import com.kata.spring.rest.kataspringrest.entities.Role;
import com.kata.spring.rest.kataspringrest.entities.User;
import com.kata.spring.rest.kataspringrest.repositories.RoleRepository;
import com.kata.spring.rest.kataspringrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return user.get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(long id) {
        return userRepository.findById(id).get();
    }


    @Override
    @Transactional
    public boolean saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            System.out.println("Пользователь с таким именем уже существует!");
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }


    @Override
    @Transactional
    public void saveRole(Role role) {
        if (roleRepository.findByName(role.getName()).isEmpty()) {
            roleRepository.save(role);
        } else {
            role = roleRepository.findByName(role.getName()).get();
        }
    }

    @Override
    public void saveUserRole(User user, Role role) {
        Role role1 = roleRepository.findByName(role.getName()).get();
        user.setRoles(Collections.singleton(role1));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
       userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getPassword().isEmpty()) {
            user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }
}
