package com.test.banshee.service;

import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.CustomerSummaryDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import org.springframework.data.domain.Page;

public interface CustomerService {

    GetCustomerDTO getCustomer(long customerId);

    Page<CustomerSummaryDTO> getCustomers(int page, int pageSize);

    long createCustomer(CreateCustomerDTO createCustomerDTO);

    void updateCustomer(long customerId, UpdateCustomerDTO updateCustomerDTO);

    void deleteCustomer(long customerId);

}
