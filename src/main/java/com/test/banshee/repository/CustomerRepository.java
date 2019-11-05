package com.test.banshee.repository;

import com.test.banshee.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByNit(String nit);

}
