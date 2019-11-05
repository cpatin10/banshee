package com.test.banshee.service;

import com.test.banshee.repository.CustomerRepository;
import com.test.banshee.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }

}