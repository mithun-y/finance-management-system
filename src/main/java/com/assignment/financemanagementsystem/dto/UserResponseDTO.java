package com.assignment.financemanagementsystem.dto;

import com.assignment.financemanagementsystem.model.Role;
import com.assignment.financemanagementsystem.model.Status;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private Role role;
    private Status status;
}
