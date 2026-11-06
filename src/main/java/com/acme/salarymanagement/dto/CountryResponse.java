
package com.acme.salarymanagement.dto;

public class CountryResponse {

    private Long id;
    private String countryName;
    private String countryCode;
    private String currencyCode;
    private String currencyName;
    private String currencySymbol;
    private Boolean active;

    public CountryResponse(Long id, String countryName,
            String countryCode, String currencyCode,
            String currencyName, String currencySymbol,
            Boolean active) {

        this.id = id;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public Boolean getActive() {
        return active;
    }
}
