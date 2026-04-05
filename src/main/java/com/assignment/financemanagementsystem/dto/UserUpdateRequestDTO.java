package com.assignment.financemanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Valid Email address")
    private String email;
    @NotBlank(message = "Role can required")
    private String role;
    @NotBlank(message = "Status is required")
    private String status;
}