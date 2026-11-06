
package com.acme.salarymanagement.service;

import java.util.List;

import com.acme.salarymanagement.dto.CountryRequest;
import com.acme.salarymanagement.dto.CountryResponse;

public interface CountryService {

    CountryResponse createCountry(CountryRequest request);

    List<CountryResponse> getAllCountries();

    CountryResponse getCountryById(Long id);

    CountryResponse updateCountry(Long id, CountryRequest request);

    void deleteCountry(Long id);
}
