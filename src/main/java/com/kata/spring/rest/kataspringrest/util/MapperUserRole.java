package com.kata.spring.rest.kataspringrest.util;

import com.kata.spring.rest.kataspringrest.dto.UserDTO;
import com.kata.spring.rest.kataspringrest.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUserRole {

    private final ModelMapper modelMapper;

    public MapperUserRole(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public  User convertToUser(UserDTO user) {
        return modelMapper.map(user, User.class);
    }
}
