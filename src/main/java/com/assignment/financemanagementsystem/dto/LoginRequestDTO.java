package com.assignment.financemanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Email(message = "Email not in correct format")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}