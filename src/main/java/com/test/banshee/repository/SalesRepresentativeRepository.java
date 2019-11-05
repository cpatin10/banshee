package com.test.banshee.repository;

import com.test.banshee.domain.City;
import com.test.banshee.domain.SalesRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesRepresentativeRepository extends JpaRepository<SalesRepresentative, Long> {

    Optional<SalesRepresentative> findByIdentificationNumber(String identificationNumber);

}
