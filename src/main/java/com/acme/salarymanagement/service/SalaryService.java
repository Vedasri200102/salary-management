
package com.acme.salarymanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.acme.salarymanagement.dto.SalaryRequest;
import com.acme.salarymanagement.dto.SalaryResponse;

public interface SalaryService {

    // Create a salary record
    SalaryResponse createSalary(SalaryRequest request);

    // Get all salary records with pagination
    Page<SalaryResponse> getAllSalaries(Pageable pageable);

    // Get salary record by ID
    SalaryResponse getSalaryById(Long id);

    // Get salary history for an employee
    Page<SalaryResponse> getSalaryHistoryByEmployeeId(
            Long employeeId, Pageable pageable);
}
