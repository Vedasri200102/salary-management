
package com.acme.salarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CountryRequest {

    @NotBlank(message = "Country name is required")
    @Size(max = 100)
    private String countryName;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 2)
    private String countryCode;

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3)
    private String currencyCode;

    @NotBlank(message = "Currency name is required")
    private String currencyName;

    @NotBlank(message = "Currency symbol is required")
    private String currencySymbol;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}
