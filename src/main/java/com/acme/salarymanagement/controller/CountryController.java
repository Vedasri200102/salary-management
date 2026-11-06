
package com.acme.salarymanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.acme.salarymanagement.dto.CountryRequest;
import com.acme.salarymanagement.dto.CountryResponse;
import com.acme.salarymanagement.service.CountryService;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<CountryResponse> createCountry(
            @Valid @RequestBody CountryRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(countryService.createCountry(request));
    }

    @GetMapping
    public ResponseEntity<List<CountryResponse>> getAllCountries() {

        return ResponseEntity.ok(
                countryService.getAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getCountryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                countryService.getCountryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryRequest request) {

        return ResponseEntity.ok(
                countryService.updateCountry(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(
            @PathVariable Long id) {

        countryService.deleteCountry(id);

        return ResponseEntity.noContent().build();
    }
}
