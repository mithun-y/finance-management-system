package com.assignment.financemanagementsystem.controller;


import com.assignment.financemanagementsystem.dto.LoginRequestDTO;
import com.assignment.financemanagementsystem.dto.LoginResponseDTO;
import com.assignment.financemanagementsystem.exception.InvalidPasswordException;
import com.assignment.financemanagementsystem.exception.UserDisabledException;
import com.assignment.financemanagementsystem.exception.UserNotFoundException;
import com.assignment.financemanagementsystem.model.Status;
import com.assignment.financemanagementsystem.model.User;
import com.assignment.financemanagementsystem.repository.UserRepository;
import com.assignment.financemanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        User user=userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()->new UserNotFoundException("User not found"));

        if (user.getStatus() == Status.INACTIVE) {
            throw new UserDisabledException("User is disabled(INACTIVE)");
        }
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty()){
            throw new InvalidPasswordException("Invalid credentials");
        }

        String token= tokenOptional.get();
        LoginResponseDTO loginResponseDTO=LoginResponseDTO.builder().token(token)
                .type("Bearer")
                .role(user.getRole().toString())
                .email(user.getEmail())
                .build();
        return ResponseEntity.ok(loginResponseDTO);
    }

}