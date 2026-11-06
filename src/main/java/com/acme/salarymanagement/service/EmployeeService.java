
package com.acme.salarymanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.acme.salarymanagement.dto.EmployeeRequest;
import com.acme.salarymanagement.entity.Employee;

public interface EmployeeService {

    // Create employee
    Employee createEmployee(EmployeeRequest request);

    // Get all employees with pagination
    Page<Employee> getAllEmployees(Pageable pageable,String keyword);

    // Get employee by ID
    Employee getEmployeeById(Long id);

    // Update employee
    Employee updateEmployee(Long id, EmployeeRequest request);

    // Delete employee
    void deleteEmployee(Long id);

}
