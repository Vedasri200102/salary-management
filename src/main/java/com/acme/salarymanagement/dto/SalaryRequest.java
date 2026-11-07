
package com.acme.salarymanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.acme.salarymanagement.entity.PayFrequency;

public class SalaryRequest {

    @NotNull
    @Positive
    private Long employeeId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal basicSalary;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal allowances = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal bonus = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal deductions = BigDecimal.ZERO;

    @NotNull
    private PayFrequency payFrequency;

    @NotNull
    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getAllowances() {
        return allowances;
    }

    public void setAllowances(BigDecimal allowances) {
        this.allowances = allowances;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    public PayFrequency getPayFrequency() {
        return payFrequency;
    }

    public void setPayFrequency(PayFrequency payFrequency) {
        this.payFrequency = payFrequency;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}
