package com.acme.salarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acme.salarymanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeCode(String employeeCode);
    boolean existsByDepartment_Id(Long departmentId);
    boolean existsByEmail(String email);
    
    boolean existsByCountry_Id(Long countryId);
    
    @Query("""
    	    SELECT e FROM Employee e
    	    WHERE LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	       OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	       OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	       OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	    """)
    	Page<Employee> searchEmployees(
    	        @Param("keyword") String keyword,
    	        Pageable pageable
    	);
}
