
package com.acme.salarymanagement.mapper;

import org.springframework.stereotype.Component;

import com.acme.salarymanagement.dto.EmployeeRequest;
import com.acme.salarymanagement.dto.EmployeeResponse;
import com.acme.salarymanagement.entity.Employee;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request) {

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
//        employee.setDepartment(request.getDepartment());
//        employee.setCountry(request.getCountry());
        employee.setJoiningDate(request.getJoiningDate());

        return employee;
    }

    public EmployeeResponse toResponse(Employee employee) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setEmployeeCode(employee.getEmployeeCode());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
//        response.setDepartment(employee.getDepartment());
//        response.setCountry(employee.getCountry());
        response.setJoiningDate(employee.getJoiningDate());
        response.setEmploymentStatus(employee.getEmploymentStatus());

        return response;
    }
}
