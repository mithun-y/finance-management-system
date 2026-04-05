package com.assignment.financemanagementsystem.dto;

import com.assignment.financemanagementsystem.model.RecordType;
import com.assignment.financemanagementsystem.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordRequestDTO {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Category cannot be empty")
    @Size(max = 30, message = "Category must be less than 30 characters")
    private String category;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @NotBlank
    @Size(max = 200, message = "Notes cannot exceed 200 characters")
    private String notes;

}
