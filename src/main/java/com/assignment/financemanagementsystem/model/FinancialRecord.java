package com.assignment.financemanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    private RecordType type;

    @NotBlank(message = "Category cannot be empty")
    @Size(max = 30, message = "Category must be less than 30 characters")
    private String category;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @Size(max = 200, message = "Notes cannot exceed 200 characters")
    private String notes;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

}