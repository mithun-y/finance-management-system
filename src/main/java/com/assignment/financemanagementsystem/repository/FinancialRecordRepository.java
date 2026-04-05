package com.assignment.financemanagementsystem.repository;

import com.assignment.financemanagementsystem.model.FinancialRecord;
import com.assignment.financemanagementsystem.model.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByTypeAndIsDeletedFalse(RecordType type);

    List<FinancialRecord> findByCategoryAndIsDeletedFalse(String category);

    List<FinancialRecord> findByDateBetweenAndIsDeletedFalse(LocalDate start, LocalDate end);

    List<FinancialRecord> findByIsDeletedFalse();

    Page<FinancialRecord> findByIsDeletedFalse(Pageable pageable);


    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type AND f.isDeleted = false")
    Double getTotalByType(@Param("type") RecordType type);

    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f WHERE f.isDeleted = false GROUP BY f.category")
    List<Object[]> getCategorySummary();

    @Query("SELECT f FROM FinancialRecord f WHERE f.isDeleted = false ORDER BY f.date DESC")
    List<FinancialRecord> findAllOrderByDateDesc();

    @Query("""
    SELECT EXTRACT(MONTH FROM f.date), f.type, SUM(f.amount)
    FROM FinancialRecord f
    WHERE f.isDeleted = false
    GROUP BY EXTRACT(MONTH FROM f.date), f.type
    ORDER BY EXTRACT(MONTH FROM f.date)""")
    List<Object[]> getMonthlyTrends();

}