
package com.acme.salarymanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.salarymanagement.entity.Country;

@Repository
public interface CountryRepository
        extends JpaRepository<Country, Long> {

    boolean existsByCountryName(String countryName);

    boolean existsByCountryCode(String countryCode);

    Optional<Country> findByCountryName(String countryName);
}
