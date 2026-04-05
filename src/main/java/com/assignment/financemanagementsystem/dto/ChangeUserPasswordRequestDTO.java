package com.assignment.financemanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangeUserPasswordRequestDTO {

    @NotBlank(message = "Password cannot be Blank")
    private String oldPassword;

    @NotBlank(message = "New Password cannot be Blank")
    @Size(min = 6)
    private String newPassword;
}