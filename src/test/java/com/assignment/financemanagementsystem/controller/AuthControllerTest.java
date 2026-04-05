package com.assignment.financemanagementsystem.controller;

import com.assignment.financemanagementsystem.dto.LoginRequestDTO;
import com.assignment.financemanagementsystem.dto.LoginResponseDTO;
import com.assignment.financemanagementsystem.exception.InvalidPasswordException;
import com.assignment.financemanagementsystem.exception.UserDisabledException;
import com.assignment.financemanagementsystem.exception.UserNotFoundException;
import com.assignment.financemanagementsystem.model.Role;
import com.assignment.financemanagementsystem.model.Status;
import com.assignment.financemanagementsystem.model.User;
import com.assignment.financemanagementsystem.repository.UserRepository;
import com.assignment.financemanagementsystem.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    private LoginRequestDTO loginRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("test@example.com");
        loginRequestDTO.setPassword("password");

        user = new User();
        user.setEmail("test@example.com");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
    }

    @Test
    void login_success() {
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(authService.authenticate(loginRequestDTO))
                .thenReturn(Optional.of("mock-jwt-token"));

        ResponseEntity<LoginResponseDTO> response =
                authController.login(loginRequestDTO);

        assertEquals(200, response.getStatusCode().value());

        LoginResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals("mock-jwt-token", body.getToken());
        assertEquals("Bearer", body.getType());
        assertEquals("ADMIN", body.getRole());
        assertEquals("test@example.com", body.getEmail());
    }

    @Test
    void login_userNotFound() {
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                authController.login(loginRequestDTO)
        );
    }

    @Test
    void login_userInactive() {
        user.setStatus(Status.INACTIVE);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        assertThrows(UserDisabledException.class, () ->
                authController.login(loginRequestDTO)
        );
    }


    @Test
    void login_invalidPassword() {
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(authService.authenticate(loginRequestDTO))
                .thenReturn(Optional.empty());

        assertThrows(InvalidPasswordException.class, () ->
                authController.login(loginRequestDTO)
        );
    }
}