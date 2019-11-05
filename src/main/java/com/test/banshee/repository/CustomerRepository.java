package com.test.banshee.repository;

import com.test.banshee.domain.Customer;
import com.test.banshee.dto.customer.CustomerSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByNit(String nit);

    Page<CustomerSummary> findBy(Pageable pageable);

}
