package com.assignment.financemanagementsystem.controller;

import com.assignment.financemanagementsystem.dto.*;
import com.assignment.financemanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserRequestDTO userRequestDTO;
    private UserUpdateRequestDTO updateDTO;
    private ChangeUserPasswordRequestDTO passwordDTO;
    private UpdateStatusRequestDTO statusDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        userRequestDTO = new UserRequestDTO();
        updateDTO = new UserUpdateRequestDTO();
        passwordDTO = new ChangeUserPasswordRequestDTO();
        statusDTO = new UpdateStatusRequestDTO();

        responseDTO = new UserResponseDTO();
        responseDTO.setEmail("test@example.com");
    }


    @Test
    void createUser_success() {

        // GIVEN
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Mithun");
        userRequestDTO.setEmail("mithun@test.com");
        userRequestDTO.setPassword("password123");
        userRequestDTO.setRole("ADMIN");
        userRequestDTO.setStatus("ACTIVE");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId("1L");
        responseDTO.setName("Mithun");

        when(userService.createUser(any(UserRequestDTO.class)))
                .thenReturn(responseDTO);

        // WHEN
        ResponseEntity<ApiResponse<UserResponseDTO>> response =
                userController.createUser(userRequestDTO);

        // THEN
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully", response.getBody().getMessage());
        assertEquals(responseDTO, response.getBody().getResult());
    }


    @Test
    void getUsers_success() {
        List<UserResponseDTO> users = List.of(responseDTO);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserResponseDTO>> response =
                userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void updateUser_success() {
        when(userService.updateUser(any(UserUpdateRequestDTO.class), eq(1L)))
                .thenReturn(responseDTO);

        ResponseEntity<ApiResponse<UserResponseDTO>> response =
                userController.updateUser(updateDTO, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated User successfully", response.getBody().getMessage());
    }

    @Test
    void changePassword_success() {
        doNothing().when(userService)
                .changePassword(eq(1L), any(ChangeUserPasswordRequestDTO.class));

        ResponseEntity<ApiResponse> response =
                userController.changePassword(1L, passwordDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password changed successfully", response.getBody().getMessage());
    }

    @Test
    void updateStatus_success() {
        when(userService.updateStatus(eq(1L), any(UpdateStatusRequestDTO.class)))
                .thenReturn(responseDTO);

        ResponseEntity<ApiResponse<UserResponseDTO>> response =
                userController.updateStatus(1L, statusDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Status successfully", response.getBody().getMessage());
    }
}