package com.assignment.financemanagementsystem.dto;

import com.assignment.financemanagementsystem.model.RecordType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordResponseDTO {

    private String id;
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String notes;
}
