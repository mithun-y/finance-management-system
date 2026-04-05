package com.assignment.financemanagementsystem.mapper;

import com.assignment.financemanagementsystem.dto.RecordRequestDTO;
import com.assignment.financemanagementsystem.dto.RecordResponseDTO;
import com.assignment.financemanagementsystem.model.FinancialRecord;
import com.assignment.financemanagementsystem.model.RecordType;


public class RecordMapper {
    public static RecordResponseDTO toDTO(FinancialRecord record) {
        RecordResponseDTO dto = new RecordResponseDTO();
        dto.setId(record.getId().toString());
        dto.setAmount(record.getAmount());
        dto.setDate(record.getDate());
        dto.setType(record.getType());
        dto.setCategory(record.getCategory());
        dto.setNotes(record.getNotes());
        return dto;
    }

    public static FinancialRecord toModel(RecordRequestDTO recordRequestDTO) {
        FinancialRecord record = new FinancialRecord();
        record.setAmount(recordRequestDTO.getAmount());
        record.setDate(recordRequestDTO.getDate());
        record.setType(RecordType.valueOf(recordRequestDTO.getType().trim().toUpperCase()));
        record.setCategory(recordRequestDTO.getCategory());
        record.setNotes(recordRequestDTO.getNotes());
        return record;
    }
}