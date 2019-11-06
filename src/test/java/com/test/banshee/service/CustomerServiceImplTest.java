package com.test.banshee.service;

import com.test.banshee.domain.Customer;
import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.CustomerSummary;
import com.test.banshee.dto.customer.CustomerSummaryDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import com.test.banshee.exception.CustomerAlreadyExistsException;
import com.test.banshee.exception.notfound.CustomerNotFoundException;
import com.test.banshee.repository.CustomerRepository;
import com.test.banshee.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static com.test.banshee.constants.CustomerTestConstants.ANY_CUSTOMER_ID;
import static com.test.banshee.constants.CustomerTestConstants.ANY_NIT;
import static com.test.banshee.constants.GeneralTestConstants.ANY_PAGE;
import static com.test.banshee.constants.GeneralTestConstants.ANY_PAGE_SIZE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

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

    @Test
    void shouldGetCustomer() {
        final Customer customer = new Customer();
        final GetCustomerDTO getCustomerDTO = GetCustomerDTO.builder().build();

        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.of(customer));
        given(customerMapper.customerToGetCustomerDTO(customer)).willReturn(getCustomerDTO);

        final GetCustomerDTO result = customerService.getCustomer(ANY_CUSTOMER_ID);

        assertThat(result, is(getCustomerDTO));

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
        then(customerMapper).should(times(1)).customerToGetCustomerDTO(customer);
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenGettingNonExistentUser() {
        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(ANY_CUSTOMER_ID));

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
    }

    @Test
    void shouldGetCustomers() {
        final CustomerSummary customerSummary = Mockito.mock(CustomerSummary.class);
        final Page<CustomerSummary> customerSummaries = new PageImpl<>(Collections.singletonList(customerSummary));
        final CustomerSummaryDTO customerSummaryDTO = CustomerSummaryDTO.builder().build();

        given(customerRepository.findBy(any(Pageable.class))).willReturn(customerSummaries);
        given(customerMapper.customerSummaryToCustomerSummaryDTO(customerSummary)).willReturn(customerSummaryDTO);

        final Page<CustomerSummaryDTO> result = customerService.getCustomers(ANY_PAGE, ANY_PAGE_SIZE);

        assertThat(result.getTotalElements(), is(1L));
        assertThat(result.iterator().next(), is(customerSummaryDTO));

        then(customerRepository).should(times(1)).findBy(any(Pageable.class));
        then(customerMapper).should(times(1)).customerSummaryToCustomerSummaryDTO(customerSummary);
    }

    @Test
    void shouldCreateCustomer() {
        final CreateCustomerDTO createCustomerDTO = CreateCustomerDTO.builder()
                .nit(ANY_NIT)
                .build();
        final Customer customer = new Customer();
        customer.setId(ANY_CUSTOMER_ID);

        given(customerRepository.existsByNit(ANY_NIT)).willReturn(false);
        given(customerMapper.createCustomerDTOToCustomer(createCustomerDTO)).willReturn(customer);
        given(customerRepository.save(customer)).willReturn(customer);

        final long result = customerService.createCustomer(createCustomerDTO);

        assertThat(result, is(ANY_CUSTOMER_ID));

        then(customerRepository).should(times(1)).existsByNit(ANY_NIT);
        then(customerMapper).should(times(1)).createCustomerDTOToCustomer(createCustomerDTO);
        then(customerRepository).should(times(1)).save(customer);
    }

    @Test
    void shouldThrowCustomerAlreadyExistsExceptionWhenCreatingCustomerWithRepeatedNit() {
        final CreateCustomerDTO createCustomerDTO = CreateCustomerDTO.builder()
                .nit(ANY_NIT)
                .build();

        given(customerRepository.existsByNit(ANY_NIT)).willReturn(true);

        assertThrows(CustomerAlreadyExistsException.class,
                () -> customerService.createCustomer(createCustomerDTO));

        then(customerRepository).should(times(1)).existsByNit(ANY_NIT);
    }

    @Test
    void shouldUpdateCustomer() {
        final Customer customer = new Customer();
        final UpdateCustomerDTO updateCustomerDTO = UpdateCustomerDTO.builder().build();

        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.of(customer));
        willDoNothing().given(customerMapper).updateCustomerDTOToCustomer(customer, updateCustomerDTO);

        customerService.updateCustomer(ANY_CUSTOMER_ID, updateCustomerDTO);

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
        then(customerMapper).should(times(1))
                .updateCustomerDTOToCustomer(customer, updateCustomerDTO);
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenUpdatingNonExistingCustomer() {
        final UpdateCustomerDTO updateCustomerDTO = UpdateCustomerDTO.builder().build();

        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.updateCustomer(ANY_CUSTOMER_ID, updateCustomerDTO));

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
    }

    @Test
    void shouldDeleteCustomer() {
        given(customerRepository.existsById(ANY_CUSTOMER_ID)).willReturn(true);
        willDoNothing().given(customerRepository).deleteById(ANY_CUSTOMER_ID);

        customerService.deleteCustomer(ANY_CUSTOMER_ID);

        then(customerRepository).should(times(1)).existsById(ANY_CUSTOMER_ID);
        then(customerRepository).should(times(1)).deleteById(ANY_CUSTOMER_ID);
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistentCustomer() {
        given(customerRepository.existsById(ANY_CUSTOMER_ID)).willReturn(false);

        customerService.deleteCustomer(ANY_CUSTOMER_ID);

        then(customerRepository).should(times(1)).existsById(ANY_CUSTOMER_ID);
        then(customerRepository).should(times(0)).deleteById(anyLong());
    }

}