package com.assignment.financemanagementsystem.service;

import com.assignment.financemanagementsystem.model.FinancialRecord;
import com.assignment.financemanagementsystem.model.RecordType;
import com.assignment.financemanagementsystem.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class DashboardService {

    private final FinancialRecordRepository repo;

    public DashboardService(FinancialRecordRepository repo) {
        this.repo = repo;
    }

    public Map<String, Object> getSummary() {

        Double income = Optional.ofNullable(repo.getTotalByType(RecordType.INCOME)).orElse(0.0);
        Double expense = Optional.ofNullable(repo.getTotalByType(RecordType.EXPENSE)).orElse(0.0);

        Map<String, Object> response = new HashMap<>();
        response.put("totalIncome", income);
        response.put("totalExpense", expense);
        response.put("netBalance", income - expense);

        return response;
    }

    public List<Map<String, Object>> getCategorySummary() {

        List<Object[]> data = repo.getCategorySummary();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", row[0]);
            map.put("total", row[1]);
            result.add(map);
        }

        return result;
    }

    public List<FinancialRecord> getRecentTransactions() {
        return repo.findAllOrderByDateDesc().stream().limit(5).toList();
    }

    public List<Map<String, Object>> getMonthlyTrends() {

        List<Object[]> data = repo.getMonthlyTrends();

        Map<String, Map<String, Double>> grouped = new TreeMap<>();

        for (Object[] row : data) {
            int month = ((Number) row[0]).intValue();
            String monthname=Month.of(month).name();  // APRIL
            String type = row[1].toString();
            Double total = ((Number) row[2]).doubleValue();

            grouped.putIfAbsent(monthname, new HashMap<>());
            grouped.get(monthname).put(type, total);
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (var entry : grouped.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", entry.getKey());
            map.put("income", entry.getValue().getOrDefault("INCOME", 0.0));
            map.put("expense", entry.getValue().getOrDefault("EXPENSE", 0.0));
            result.add(map);
        }

        return result;
    }
}