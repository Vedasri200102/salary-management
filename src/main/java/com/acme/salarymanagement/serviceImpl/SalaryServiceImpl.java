
package com.acme.salarymanagement.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.salarymanagement.dto.SalaryRequest;
import com.acme.salarymanagement.dto.SalaryResponse;
import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.entity.Salary;
import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.repository.SalaryRepository;
import com.acme.salarymanagement.service.SalaryService;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    public SalaryServiceImpl(
            SalaryRepository salaryRepository,
            EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public SalaryResponse createSalary(SalaryRequest request) {

        // Validate required fields
        if (request == null
                || request.getEmployeeId() == null
                || request.getBasicSalary() == null
                || request.getAllowances() == null
                || request.getBonus() == null
                || request.getDeductions() == null
                || request.getPayFrequency() == null
                || request.getEffectiveFrom() == null) {
            throw new IllegalArgumentException(
                    "All required salary fields must be provided");
        }

        // Validate salary amounts
        if (request.getBasicSalary().compareTo(BigDecimal.ZERO) < 0
                || request.getAllowances().compareTo(BigDecimal.ZERO) < 0
                || request.getBonus().compareTo(BigDecimal.ZERO) < 0
                || request.getDeductions().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Salary amounts cannot be negative");
        }

        // Validate effective dates
        LocalDate effectiveFrom = request.getEffectiveFrom();
        LocalDate effectiveTo = request.getEffectiveTo();

        if (effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new IllegalArgumentException(
                    "Effective To date cannot be before Effective From date");
        }

        // Find employee
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Employee not found"));

        // Calculate gross salary
        BigDecimal grossSalary = request.getBasicSalary()
                .add(request.getAllowances())
                .add(request.getBonus());

        // Validate deductions
        if (request.getDeductions().compareTo(grossSalary) > 0) {
            throw new IllegalArgumentException(
                    "Deductions cannot exceed gross salary");
        }

        // Calculate net salary
        BigDecimal netSalary = grossSalary.subtract(request.getDeductions());

        // Check currency from employee's country
        if (employee.getCountry() == null
                || employee.getCountry().getCurrencyCode() == null
                || employee.getCountry().getCurrencyCode().isBlank()) {
            throw new IllegalArgumentException(
                    "Employee country currency is not configured");
        }

        String currencyCode =
                employee.getCountry().getCurrencyCode().trim();

        // Prevent overlapping salary periods
        boolean overlapping = salaryRepository.existsOverlappingSalary(
                employee.getId(),
                effectiveFrom,
                effectiveTo
        );

        if (overlapping) {
            throw new IllegalArgumentException(
                    "Salary period overlaps with an existing salary record");
        }

        // Create a new salary record
        Salary salary = new Salary();

        salary.setEmployee(employee);
        salary.setBasicSalary(request.getBasicSalary());
        salary.setAllowances(request.getAllowances());
        salary.setBonus(request.getBonus());
        salary.setDeductions(request.getDeductions());
        salary.setGrossSalary(grossSalary);
        salary.setNetSalary(netSalary);
        salary.setCurrencyCode(currencyCode);
        salary.setPayFrequency(request.getPayFrequency());
        salary.setEffectiveFrom(effectiveFrom);
        salary.setEffectiveTo(effectiveTo);

        Salary savedSalary = salaryRepository.save(salary);

        return mapToResponse(savedSalary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalaryResponse> getAllSalaries(Pageable pageable) {

        return salaryRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public SalaryResponse getSalaryById(Long id) {

        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Salary record not found"));

        return mapToResponse(salary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalaryResponse> getSalaryHistoryByEmployeeId(
            Long employeeId, Pageable pageable) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException("Employee not found");
        }

        return salaryRepository
                .findByEmployee_IdOrderByEffectiveFromDesc(employeeId, pageable)
                .map(this::mapToResponse);
    }

    // Convert Salary entity into SalaryResponse DTO
    private SalaryResponse mapToResponse(Salary salary) {

        SalaryResponse response = new SalaryResponse();

        Employee employee = salary.getEmployee();

        response.setId(salary.getId());
        response.setEmployeeId(employee.getId());
        response.setEmployeeCode(employee.getEmployeeCode());
        response.setEmployeeName(
                employee.getFirstName() + " " + employee.getLastName());

        response.setBasicSalary(salary.getBasicSalary());
        response.setAllowances(salary.getAllowances());
        response.setBonus(salary.getBonus());
        response.setDeductions(salary.getDeductions());

        response.setGrossSalary(salary.getGrossSalary());
        response.setNetSalary(salary.getNetSalary());

        response.setCurrencyCode(salary.getCurrencyCode());
        response.setPayFrequency(salary.getPayFrequency());

        response.setEffectiveFrom(salary.getEffectiveFrom());
        response.setEffectiveTo(salary.getEffectiveTo());

        return response;
    }
}
