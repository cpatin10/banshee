package com.test.banshee.repository;

import com.test.banshee.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository  extends JpaRepository<City, Short> {

    Optional<City> findByNameAndStateNameAndStateCountryName(String cityName, String stateName, String countryName);

}
