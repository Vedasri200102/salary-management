
package com.acme.salarymanagement.serviceImpl;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.salarymanagement.dto.CountryRequest;
import com.acme.salarymanagement.dto.CountryResponse;
import com.acme.salarymanagement.entity.Country;
import com.acme.salarymanagement.repository.CountryRepository;
import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.service.CountryService;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final EmployeeRepository employeeRepository;

    public CountryServiceImpl(
            CountryRepository countryRepository,
            EmployeeRepository employeeRepository) {

        this.countryRepository = countryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public CountryResponse createCountry(CountryRequest request) {

        String countryCode = normalize(request.getCountryCode());
        String countryName = request.getCountryName().trim();

        if (countryRepository.existsByCountryCode(countryCode)) {
            throw new IllegalArgumentException(
                    "Country code already exists");
        }

        if (countryRepository.existsByCountryName(countryName)) {
            throw new IllegalArgumentException(
                    "Country name already exists");
        }

        Country country = new Country();

        country.setCountryName(countryName);
        country.setCountryCode(countryCode);
        country.setCurrencyCode(normalize(request.getCurrencyCode()));
        country.setCurrencyName(request.getCurrencyName().trim());
        country.setCurrencySymbol(request.getCurrencySymbol().trim());
        country.setActive(true);

        return mapToResponse(countryRepository.save(country));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponse> getAllCountries() {

        return countryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CountryResponse getCountryById(Long id) {

        return mapToResponse(findCountry(id));
    }

    @Override
    public CountryResponse updateCountry(
            Long id, CountryRequest request) {

        Country country = findCountry(id);

        String countryCode = normalize(request.getCountryCode());
        String countryName = request.getCountryName().trim();

        if (!country.getCountryCode().equals(countryCode)
                && countryRepository.existsByCountryCode(countryCode)) {
            throw new IllegalArgumentException(
                    "Country code already exists");
        }

        if (!country.getCountryName().equalsIgnoreCase(countryName)
                && countryRepository.existsByCountryName(countryName)) {
            throw new IllegalArgumentException(
                    "Country name already exists");
        }

        country.setCountryName(countryName);
        country.setCountryCode(countryCode);
        country.setCurrencyCode(normalize(request.getCurrencyCode()));
        country.setCurrencyName(request.getCurrencyName().trim());
        country.setCurrencySymbol(request.getCurrencySymbol().trim());

        return mapToResponse(countryRepository.save(country));
    }

    @Override
    public void deleteCountry(Long id) {

        Country country = findCountry(id);

        if (employeeRepository.existsByCountry_Id(id)) {
            throw new IllegalStateException(
                    "Cannot delete country assigned to employees");
        }

        country.setActive(false);

        countryRepository.save(country);
    }

    private Country findCountry(Long id) {

        return countryRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Country not found with ID: " + id));
    }

    private String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private CountryResponse mapToResponse(Country country) {

        return new CountryResponse(
                country.getId(),
                country.getCountryName(),
                country.getCountryCode(),
                country.getCurrencyCode(),
                country.getCurrencyName(),
                country.getCurrencySymbol(),
                country.getActive());
    }
}
