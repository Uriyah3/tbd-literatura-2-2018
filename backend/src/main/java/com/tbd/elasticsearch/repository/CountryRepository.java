package com.tbd.elasticsearch.repository;

import com.tbd.elasticsearch.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findCountryByName(String name);
}
