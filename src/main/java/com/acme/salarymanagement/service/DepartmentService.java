
package com.acme.salarymanagement.service;

import java.util.List;

import com.acme.salarymanagement.dto.DepartmentRequest;
import com.acme.salarymanagement.dto.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id,
            DepartmentRequest request);

    void deleteDepartment(Long id);
}
