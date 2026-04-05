package com.assignment.financemanagementsystem.controller;

import com.assignment.financemanagementsystem.dto.ApiResponse;
import com.assignment.financemanagementsystem.dto.RecordRequestDTO;
import com.assignment.financemanagementsystem.dto.RecordResponseDTO;
import com.assignment.financemanagementsystem.model.RecordType;
import com.assignment.financemanagementsystem.service.FinancialRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialRecordControllerTest {

    @InjectMocks
    private FinancialRecordController controller;

    @Mock
    private FinancialRecordService service;

    private RecordRequestDTO requestDTO;
    private RecordResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new RecordRequestDTO();
        requestDTO.setAmount(1000d);
        requestDTO.setCategory("Food");
        requestDTO.setType(RecordType.EXPENSE.toString());
        requestDTO.setDate(LocalDate.of(2026, 4, 1));
        requestDTO.setNotes("Lunch");

        responseDTO = new RecordResponseDTO();
        responseDTO.setId("1L");
        responseDTO.setAmount(1000d);
        responseDTO.setCategory("Food");
        responseDTO.setType(RecordType.EXPENSE);
        responseDTO.setDate(LocalDate.of(2026, 4, 1));
        responseDTO.setNotes("Lunch");
    }

    @Test
    void getByType_success() {
        when(service.getByType(RecordType.EXPENSE))
                .thenReturn(List.of(responseDTO));

        ResponseEntity<List<RecordResponseDTO>> response =
                controller.getByType("expense");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getByCategory_success() {
        when(service.getByCategory("Food"))
                .thenReturn(List.of(responseDTO));

        ResponseEntity<List<RecordResponseDTO>> response =
                controller.getByCategory("Food");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getByDateRange_success() {
        LocalDate start = LocalDate.of(2026, 4, 1);
        LocalDate end = LocalDate.of(2026, 4, 5);

        when(service.getByDateRange(start, end))
                .thenReturn(List.of(responseDTO));

        ResponseEntity<List<RecordResponseDTO>> response =
                controller.getByDateRange("2026-04-01", "2026-04-05");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create_success() {
        when(service.createRecord(any(RecordRequestDTO.class)))
                .thenReturn(responseDTO);

        ResponseEntity<ApiResponse<RecordResponseDTO>> response =
                controller.create(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record created successfully", response.getBody().getMessage());
    }

    @Test
    void getAll_success() {
        when(service.getAllRecords())
                .thenReturn(List.of(responseDTO));

        ResponseEntity<List<RecordResponseDTO>> response =
                controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getById_success() {
        when(service.getByRecordId(1L))
                .thenReturn(responseDTO);

        ResponseEntity<RecordResponseDTO> response =
                controller.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1L", response.getBody().getId());
    }

    @Test
    void update_success() {
        when(service.updateRecord(eq(1L), any(RecordRequestDTO.class)))
                .thenReturn(responseDTO);

        ResponseEntity<ApiResponse<RecordResponseDTO>> response =
                controller.update(1L, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated record successfully", response.getBody().getMessage());
    }

    @Test
    void delete_success() {
        doNothing().when(service).deleteRecord(1L);

        ResponseEntity<ApiResponse> response =
                controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record deleted successfully", response.getBody().getMessage());
    }
}