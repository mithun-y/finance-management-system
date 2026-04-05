package com.assignment.financemanagementsystem.service;

import com.assignment.financemanagementsystem.dto.LoginRequestDTO;
import com.assignment.financemanagementsystem.dto.LoginResponseDTO;
import com.assignment.financemanagementsystem.repository.UserRepository;
import com.assignment.financemanagementsystem.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<String> authenticate(@Valid LoginRequestDTO loginRequestDTO) {
        Optional<String> token=userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(),
                        u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(),u.getRole().name()));

        return token;
    }
}