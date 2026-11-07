package com.acme.salarymanagement.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acme.salarymanagement.entity.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    // Fetch salary history for a particular employee
    Page<Salary> findByEmployee_IdOrderByEffectiveFromDesc(
            Long employeeId,
            Pageable pageable
    );

    // Check whether an employee already has a salary record
    // overlapping the requested effective date range
    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
        FROM Salary s
        WHERE s.employee.id = :employeeId
          AND (:effectiveTo IS NULL OR s.effectiveFrom <= :effectiveTo)
          AND (s.effectiveTo IS NULL OR s.effectiveTo >= :effectiveFrom)
    """)
    boolean existsOverlappingSalary(
            @Param("employeeId") Long employeeId,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTo") LocalDate effectiveTo
    );
}