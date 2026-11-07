
package com.acme.salarymanagement.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.acme.salarymanagement.dto.EmployeeRequest;
import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.entity.Department;
import com.acme.salarymanagement.entity.Country;
import com.acme.salarymanagement.entity.EmploymentStatus;
import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.repository.DepartmentRepository;
import com.acme.salarymanagement.repository.CountryRepository;
import com.acme.salarymanagement.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CountryRepository countryRepository;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            CountryRepository countryRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Employee createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmployeeCode(
                request.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists");
        }

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Department department = departmentRepository
                .findByDepartmentName(request.getDepartment().trim())
                .orElseThrow(() ->
                    new RuntimeException("Department not found: "
                            + request.getDepartment()));

        Country country = countryRepository
                .findByCountryName(request.getCountry().trim())
                .orElseThrow(() ->
                    new RuntimeException("Country not found: "
                            + request.getCountry()));

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);
        employee.setCountry(country);
        employee.setJoiningDate(request.getJoiningDate());
        employee.setEmploymentStatus(EmploymentStatus.ACTIVE);

        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable, String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll(pageable);
        }

        return employeeRepository.searchEmployees(keyword.trim(), pageable);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Employee not found: " + id));
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = getEmployeeById(id);

        if (!employee.getEmployeeCode().equals(request.getEmployeeCode())
                && employeeRepository.existsByEmployeeCode(
                        request.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists");
        }

        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Department department = departmentRepository
                .findByDepartmentName(request.getDepartment().trim())
                .orElseThrow(() ->
                    new RuntimeException("Department not found: "
                            + request.getDepartment()));

        Country country = countryRepository
                .findByCountryName(request.getCountry().trim())
                .orElseThrow(() ->
                    new RuntimeException("Country not found: "
                            + request.getCountry()));

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);
        employee.setCountry(country);
        employee.setJoiningDate(request.getJoiningDate());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
}
