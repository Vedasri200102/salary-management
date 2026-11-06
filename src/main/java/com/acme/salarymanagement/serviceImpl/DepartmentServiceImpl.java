
package com.acme.salarymanagement.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.salarymanagement.dto.DepartmentRequest;
import com.acme.salarymanagement.dto.DepartmentResponse;
import com.acme.salarymanagement.entity.Department;
import com.acme.salarymanagement.repository.DepartmentRepository;
import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository) {

        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByDepartmentCode(
                request.getDepartmentCode())) {
            throw new IllegalArgumentException(
                    "Department code already exists");
        }

        if (departmentRepository.existsByDepartmentName(
                request.getDepartmentName())) {
            throw new IllegalArgumentException(
                    "Department name already exists");
        }

        Department department = new Department();

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setActive(true);

        return mapToResponse(departmentRepository.save(department));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = findDepartment(id);

        return mapToResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(
            Long id, DepartmentRequest request) {

        Department department = findDepartment(id);

        if (!department.getDepartmentCode().equals(
                request.getDepartmentCode())
                && departmentRepository.existsByDepartmentCode(
                        request.getDepartmentCode())) {
            throw new IllegalArgumentException(
                    "Department code already exists");
        }

        if (!department.getDepartmentName().equalsIgnoreCase(
                request.getDepartmentName())
                && departmentRepository.existsByDepartmentName(
                        request.getDepartmentName())) {
            throw new IllegalArgumentException(
                    "Department name already exists");
        }

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());

        return mapToResponse(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = findDepartment(id);

        if (employeeRepository.existsByDepartment_Id(id)) {
            throw new IllegalStateException(
                    "Cannot delete department assigned to employees");
        }

        department.setActive(false);

        departmentRepository.save(department);
    }

    private Department findDepartment(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Department not found with ID: " + id));
    }

    private DepartmentResponse mapToResponse(Department department) {

        return new DepartmentResponse(
                department.getId(),
                department.getDepartmentCode(),
                department.getDepartmentName(),
                department.getActive());
    }
}
