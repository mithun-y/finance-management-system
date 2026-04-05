package com.assignment.financemanagementsystem.controller;


import com.assignment.financemanagementsystem.dto.*;
import com.assignment.financemanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("User created successfully",userResponseDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO, @PathVariable Long id){
        System.out.println("Update User controller");
        UserResponseDTO userResponseDTO=userService.updateUser(userUpdateRequestDTO,id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Updated User successfully",userResponseDTO));
    }

    @PreAuthorize("@userService.isOwner(#id, authentication.name) or hasRole('ADMIN')")
    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResponse> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO) {

        userService.changePassword(id, changeUserPasswordRequestDTO);
        return ResponseEntity.ok(new ApiResponse("Password changed successfully",true));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequestDTO dto) {

        UserResponseDTO response = userService.updateStatus(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("Updated Status successfully",response));
    }


    //-> DONT USE DELETE, INSTEAD MAKE THE USER INACTIVE

//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
//        userService.deleteUser(id);
//        return ResponseEntity.ok(new ApiResponse("User deleted successfully",true));
//    }
}