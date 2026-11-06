
package com.acme.salarymanagement.dto;

public class DepartmentResponse {

    private Long id;
    private String departmentCode;
    private String departmentName;
    private Boolean active;

    public DepartmentResponse() {
    }

    public DepartmentResponse(Long id, String departmentCode,
            String departmentName, Boolean active) {
        this.id = id;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Boolean getActive() {
        return active;
    }
}
