package com.assignment.financemanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStatusRequestDTO {

    @NotBlank(message = "Status is required")
    private String status;
}