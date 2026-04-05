package com.assignment.financemanagementsystem.mapper;

import com.assignment.financemanagementsystem.dto.UserRequestDTO;
import com.assignment.financemanagementsystem.dto.UserResponseDTO;
import com.assignment.financemanagementsystem.model.Role;
import com.assignment.financemanagementsystem.model.Status;
import com.assignment.financemanagementsystem.model.User;

public class UserMapper {
    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId().toString());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setStatus(user.getStatus());
        return userResponseDTO;
    }

    public static User toModel(UserRequestDTO userRequestDTO){
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setRole(Role.valueOf(userRequestDTO.getRole().trim().toUpperCase()));
        user.setStatus(Status.valueOf(userRequestDTO.getStatus().trim().toUpperCase()));
        return user;
    }
}
