package com.test.banshee.service;

import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import org.springframework.data.domain.Page;

public interface CustomerService {

    Page<GetCustomerDTO> getCustomers(int page, int pageSize);

    long createCustomer(CreateCustomerDTO createCustomerDTO);

    void updateCustomer(long customerId, UpdateCustomerDTO updateCustomerDTO);

    void deleteCustomer(long customerId);

}
