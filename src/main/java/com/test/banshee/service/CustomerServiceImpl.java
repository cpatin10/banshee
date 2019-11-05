package com.test.banshee.service;

import com.test.banshee.domain.Customer;
import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.CustomerSummaryDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import com.test.banshee.exception.CustomerAlreadyExistsException;
import com.test.banshee.exception.notfound.CustomerNotFoundException;
import com.test.banshee.repository.CustomerRepository;
import com.test.banshee.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    @Override
    public GetCustomerDTO getCustomer(final long customerId) {
        final Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return customerMapper.customerToGetCustomerDTO(customer);
    }

    @Override
    public Page<CustomerSummaryDTO> getCustomers(final int page, final int pageSize) {
        final PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("fullName").and(Sort.by("nit")));
        return customerRepository.findBy(pageRequest)
                .map(customerMapper::customerSummaryToCustomerSummaryDTO);
    }

    @Override
    @Transactional
    public long createCustomer(final CreateCustomerDTO createCustomerDTO) {
        if (customerRepository.existsByNit(createCustomerDTO.getNit())) {
            throw new CustomerAlreadyExistsException(createCustomerDTO.getNit());
        }
        final Customer mappedCustomer = customerMapper.createCustomerDTOToCustomer(createCustomerDTO);
        final Customer persistedCustomer = customerRepository.save(mappedCustomer);
        return persistedCustomer.getId();
    }

    @Override
    @Transactional
    public void updateCustomer(final long customerId, final UpdateCustomerDTO updateCustomerDTO) {
        final Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerMapper.updateCustomerDTOToCustomer(customer, updateCustomerDTO);
    }

    @Override
    @Transactional
    public void deleteCustomer(final long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        }
    }

}
