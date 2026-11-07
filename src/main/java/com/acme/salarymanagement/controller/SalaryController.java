
package com.acme.salarymanagement.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acme.salarymanagement.dto.SalaryRequest;
import com.acme.salarymanagement.dto.SalaryResponse;
import com.acme.salarymanagement.service.SalaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    // Create salary
    @PostMapping
    public ResponseEntity<SalaryResponse> createSalary(
            @Valid @RequestBody SalaryRequest request) {

        SalaryResponse response = salaryService.createSalary(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all salaries with pagination
    @GetMapping
    public ResponseEntity<Page<SalaryResponse>> getAllSalaries(
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(
                salaryService.getAllSalaries(pageable)
        );
    }

    // Get salary by ID
    @GetMapping("/{id}")
    public ResponseEntity<SalaryResponse> getSalaryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                salaryService.getSalaryById(id)
        );
    }

    // Get employee salary history
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<SalaryResponse>> getSalaryHistory(
            @PathVariable Long employeeId,
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(
                salaryService.getSalaryHistoryByEmployeeId(
                        employeeId, pageable
                )
        );
    }
}
