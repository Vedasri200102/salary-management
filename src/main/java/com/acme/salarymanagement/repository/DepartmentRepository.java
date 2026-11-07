
package com.acme.salarymanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.salarymanagement.entity.Department;

@Repository
public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    boolean existsByDepartmentCode(String departmentCode);

    boolean existsByDepartmentName(String departmentName);

    Optional<Department> findByDepartmentName(String departmentName);
}
