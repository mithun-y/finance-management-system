package com.assignment.financemanagementsystem.service;

import com.assignment.financemanagementsystem.dto.RecordRequestDTO;
import com.assignment.financemanagementsystem.dto.RecordResponseDTO;
import com.assignment.financemanagementsystem.exception.InvalidTypeException;
import com.assignment.financemanagementsystem.exception.RecordNotFoundException;
import com.assignment.financemanagementsystem.mapper.RecordMapper;
import com.assignment.financemanagementsystem.model.FinancialRecord;
import com.assignment.financemanagementsystem.model.RecordType;
import com.assignment.financemanagementsystem.repository.FinancialRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class FinancialRecordService {

    private final FinancialRecordRepository repository;
    private RecordType recordType;


    //FILTER SERVICE

    public List<RecordResponseDTO> getByType(RecordType type) {
        List<FinancialRecord> records=repository.findByTypeAndIsDeletedFalse(type);

        List<RecordResponseDTO> recordResponseDTOS = records.stream()
                .map(RecordMapper::toDTO).toList();

        return recordResponseDTOS;
    }

    public List<RecordResponseDTO> getByCategory(String category) {
        List<FinancialRecord> records=repository.findByCategoryAndIsDeletedFalse(category);

        List<RecordResponseDTO> recordResponseDTOS = records.stream()
                .map(RecordMapper::toDTO).toList();

        return recordResponseDTOS;
    }

    public List<RecordResponseDTO> getByDateRange(LocalDate start, LocalDate end) {
        List<FinancialRecord> records=repository.findByDateBetweenAndIsDeletedFalse(start, end);

        List<RecordResponseDTO> recordResponseDTOS = records.stream()
                .map(RecordMapper::toDTO).toList();

        return recordResponseDTOS;
    }



    // CRUD SERVICE

    public FinancialRecordService(FinancialRecordRepository repository) {
        this.repository = repository;
    }

    public RecordResponseDTO createRecord(RecordRequestDTO recordRequestDTO) {
        try {
            recordType = recordType.valueOf(recordRequestDTO.getType().trim().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTypeException("Invalid type. Allowed: INCOME OR EXPENSE");
        }

        FinancialRecord record=RecordMapper.toModel(recordRequestDTO);
        FinancialRecord savedRecord=repository.save(record);
        return RecordMapper.toDTO(savedRecord);
    }

    public List<RecordResponseDTO> getAllRecords() {
        List<FinancialRecord> records=repository.findByIsDeletedFalse();

        List<RecordResponseDTO> recordResponseDTOS = records.stream()
                .map(RecordMapper::toDTO).toList();

        return recordResponseDTOS;
    }

    public Page<RecordResponseDTO> getAllRecordsByPagination(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FinancialRecord> recordPage = repository.findByIsDeletedFalse(pageable);

        return recordPage.map(RecordMapper::toDTO);
    }


    public RecordResponseDTO getByRecordId(Long recordId) {
        FinancialRecord record=repository.findById(recordId).orElseThrow(()-> new RecordNotFoundException("Record Not Found"));
        return RecordMapper.toDTO(record);
    }

    public RecordResponseDTO updateRecord(Long id, RecordRequestDTO recordRequestDTO) {
        FinancialRecord record = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record not found"));

        try {
            recordType = recordType.valueOf(recordRequestDTO.getType().trim().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTypeException("Invalid type. Allowed: INCOME OR EXPENSE");
        }

        record.setAmount(recordRequestDTO.getAmount());
        record.setCategory(recordRequestDTO.getCategory());
        record.setType(RecordType.valueOf(recordRequestDTO.getType().trim().toUpperCase()));
        record.setDate(recordRequestDTO.getDate());
        record.setNotes(recordRequestDTO.getNotes());

        FinancialRecord updatedRecord=repository.save(record);
        return  RecordMapper.toDTO(updatedRecord);
    }

    public void deleteRecord(Long id) {
        FinancialRecord record = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record not found"));

        record.setDeleted(true);
        repository.save(record);
    }

    public void restoreRecord(Long id) {
        FinancialRecord record = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setDeleted(false);
        repository.save(record);
    }

}