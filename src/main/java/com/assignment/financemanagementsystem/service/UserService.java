package com.assignment.financemanagementsystem.service;

import com.assignment.financemanagementsystem.dto.*;
import com.assignment.financemanagementsystem.exception.*;
import com.assignment.financemanagementsystem.mapper.UserMapper;
import com.assignment.financemanagementsystem.model.Role;
import com.assignment.financemanagementsystem.model.Status;
import com.assignment.financemanagementsystem.model.User;
import com.assignment.financemanagementsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Role role;
    private Status status;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        try {
            role = Role.valueOf(userRequestDTO.getRole().trim().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTypeException("Invalid type. Allowed: ADMIN, ANALYST, VIEWER");
        }

        try {
            status = Status.valueOf(userRequestDTO.getStatus().trim().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTypeException("Invalid type. Allowed: ACTIVE, INACTIVE");
        }

        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User user=userRepository.save(UserMapper.toModel(userRequestDTO));
        return UserMapper.toDTO(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOS = users.stream()
                .map(UserMapper::toDTO).toList();

        return userResponseDTOS;
    }

    public UserResponseDTO  updateUser(UserUpdateRequestDTO userUpdateRequestDTO, Long id) {
        System.out.println("Inside the service class");
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User Not found with ID: "+id));

        if(userRepository.existsByEmailAndIdNot(userUpdateRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException("A User with this email already exists "+userUpdateRequestDTO.getEmail());
        }

        try {
            role = Role.valueOf(userUpdateRequestDTO.getRole().trim().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTypeException("Invalid type. Allowed: ADMIN, ANALYST, VIEWER");
        }

        try {
            status = Status.valueOf(userUpdateRequestDTO.getStatus().trim().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTypeException("Invalid type. Allowed: ACTIVE, INACTIVE");
        }

        user.setName(userUpdateRequestDTO.getName());
        user.setEmail(userUpdateRequestDTO.getEmail());
        user.setRole(Role.valueOf(userUpdateRequestDTO.getRole().trim().toUpperCase()));
        user.setStatus(Status.valueOf(userUpdateRequestDTO.getStatus().trim().toUpperCase()));

        User updateduser = userRepository.save(user);
        return UserMapper.toDTO(updateduser);
    }

    public void changePassword(Long id,ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // verify old password
        if (!passwordEncoder.matches(changeUserPasswordRequestDTO.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid Password");
        }

        if (changeUserPasswordRequestDTO.getOldPassword()
                .equals(changeUserPasswordRequestDTO.getNewPassword())) {
            throw new InvalidPasswordException("New password cannot be same as old password");
        }

        user.setPassword(passwordEncoder.encode(changeUserPasswordRequestDTO.getNewPassword()));
        userRepository.save(user);
    }

    public UserResponseDTO updateStatus(Long id, UpdateStatusRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));


        try {
            status = Status.valueOf(dto.getStatus().trim().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTypeException("Invalid status. Allowed: ACTIVE, INACTIVE");
        }

        user.setStatus(status);

        return UserMapper.toDTO(userRepository.save(user));
    }

    //-> this is used to check whether the user is autheticated(principle) or not
    public boolean isOwner(Long id, String email) {
        return userRepository.findById(id)
                .map(user -> user.getEmail().equals(email))
                .orElse(false);
    }



    //-> DONT USE DELETE, INSTEAD MAKE THE USER INACTIVE

//    public void deleteUser(Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("User not found with id: " + id);
//        }
//
//        userRepository.deleteById(id);
//    }


}