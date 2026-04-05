package com.assignment.financemanagementsystem.controller;

import com.assignment.financemanagementsystem.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DashboardControllerTest {
    @InjectMocks
    private DashboardController controller;

    @Mock
    private DashboardService service;

    @Test
    void getSummary_success() {

        Map<String, Object> mockSummary = Map.of(
                "totalIncome", 50000,
                "totalExpense", 20000,
                "balance", 30000
        );

        when(service.getSummary()).thenReturn(mockSummary);

        ResponseEntity<Map<String, Object>> response =
                controller.getSummary();

        assertEquals(200, response.getStatusCode().value());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);

        assertEquals(50000, body.get("totalIncome"));
        assertEquals(20000, body.get("totalExpense"));
        assertEquals(30000, body.get("balance"));

        verify(service, times(1)).getSummary();
    }


    @Test
    void getCategorySummary_success() {

        List<Map<String, Object>> mockData = List.of(
                Map.of("category", "Food", "amount", 5000),
                Map.of("category", "Transport", "amount", 2000)
        );

        when(service.getCategorySummary()).thenReturn(mockData);

        ResponseEntity<List<Map<String, Object>>> response =
                controller.getCategorySummary();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Map<String, Object>> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());

        assertEquals("Food", body.get(0).get("category"));
        assertEquals(5000, body.get(0).get("amount"));

        verify(service).getCategorySummary();
    }

    @Test
    void getMonthlyTrends_success() {

        // Arrange
        List<Map<String, Object>> mockData = List.of(
                Map.of("month", "Jan", "income", 50000, "expense", 30000),
                Map.of("month", "Feb", "income", 60000, "expense", 35000)
        );

        when(service.getMonthlyTrends()).thenReturn(mockData);

        ResponseEntity<List<Map<String, Object>>> response =
                controller.getMonthlyTrends();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Map<String, Object>> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());

        assertEquals("Jan", body.get(0).get("month"));
        assertEquals(50000, body.get(0).get("income"));

        verify(service).getMonthlyTrends();
    }
}
