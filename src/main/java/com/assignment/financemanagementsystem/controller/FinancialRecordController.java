package com.assignment.financemanagementsystem.controller;

import com.assignment.financemanagementsystem.dto.ApiResponse;
import com.assignment.financemanagementsystem.dto.RecordRequestDTO;
import com.assignment.financemanagementsystem.dto.RecordResponseDTO;
import com.assignment.financemanagementsystem.model.RecordType;
import com.assignment.financemanagementsystem.service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }


    // FILTER RECORDS

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/type")
    public ResponseEntity<List<RecordResponseDTO>> getByType(@RequestParam String type) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByType(RecordType.valueOf(type.toUpperCase())));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/category")
    public ResponseEntity<List<RecordResponseDTO>> getByCategory(@RequestParam String category) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByCategory(category));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/date-range")
    public ResponseEntity<List<RecordResponseDTO>> getByDateRange(
            @RequestParam String start,
            @RequestParam String end) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByDateRange(
                LocalDate.parse(start),
                LocalDate.parse(end)));
    }


    // CRUD OPERATIONS
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<RecordResponseDTO>> create(@Valid @RequestBody RecordRequestDTO recordRequestDTO) {
        RecordResponseDTO record=service.createRecord(recordRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Record created successfully",record));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/all")
    public ResponseEntity<List<RecordResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRecords());
    }

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping
    public ResponseEntity<Page<RecordResponseDTO>> getAllByPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<RecordResponseDTO> records =
                service.getAllRecordsByPagination(page, size, sortBy, sortDir);

        return ResponseEntity.ok(records);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RecordResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByRecordId(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecordResponseDTO>> update(@PathVariable Long id,
                                  @Valid @RequestBody RecordRequestDTO recordRequestDTO) {
        RecordResponseDTO recordResponseDTO=service.updateRecord(id,recordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Updated record successfully",recordResponseDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.ok(new ApiResponse("Record deleted successfully",true));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/restore")
    public ResponseEntity<ApiResponse> restore(@PathVariable Long id) {
        service.restoreRecord(id);
        return ResponseEntity.ok(new ApiResponse("Record Restored successfully",true));
    }
}