package com.assignment.financemanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private final String token;
    private final String email;
    private final String role;
    private final String type;
}
