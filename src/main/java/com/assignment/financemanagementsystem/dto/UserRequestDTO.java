package com.assignment.financemanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message="Email cannot be null")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "Password cannot be Blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role cannot Null, it can be either ADMIN/ANALYST/VIEWER")
    private String role;

    @NotBlank(message = "status cannot Null, it can be either ACTIVE/INACTIVE ")
    private String status;
}
