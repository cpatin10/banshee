package com.test.banshee.service;

import com.test.banshee.domain.Customer;
import com.test.banshee.domain.Visit;
import com.test.banshee.dto.visit.CreateVisitDTO;
import com.test.banshee.dto.visit.UpdateVisitDTO;
import com.test.banshee.exception.ExceededCreditLimitException;
import com.test.banshee.exception.InsufficientCreditException;
import com.test.banshee.exception.notfound.CustomerNotFoundException;
import com.test.banshee.exception.notfound.VisitNotFoundException;
import com.test.banshee.repository.CustomerRepository;
import com.test.banshee.repository.VisitRepository;
import com.test.banshee.service.mapper.VisitMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.test.banshee.constants.CustomerTestConstants.ANY_CUSTOMER_ID;
import static com.test.banshee.constants.CustomerTestConstants.ANY_VISITS_PERCENTAGE;
import static com.test.banshee.constants.VisitTestConstants.ANOTHER_NET;
import static com.test.banshee.constants.VisitTestConstants.ANY_NET;
import static com.test.banshee.constants.VisitTestConstants.ANY_VISIT_ID;
import static com.test.banshee.constants.VisitTestConstants.ANY_VISIT_TOTAL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private VisitMapper visitMapper;

    private VisitService visitService;

    private CreateVisitDTO createVisitDTO;

    private UpdateVisitDTO updateVisitDTO;

    private Customer customer;

    private Visit visit;

    @BeforeEach
    void setUp() {
        visitService = new VisitServiceImpl(visitRepository, customerRepository, visitMapper);

        createVisitDTO = CreateVisitDTO.builder()
                .customerId(ANY_CUSTOMER_ID)
                .net(ANY_NET)
                .build();

        updateVisitDTO = UpdateVisitDTO.builder()
                .net(ANY_NET)
                .build();

        customer = new Customer();
        customer.setVisitPercentage(ANY_VISITS_PERCENTAGE);
        customer.setAvailableCredit(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE));
        customer.setCreditLimit(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE));

        visit = new Visit();
        visit.setId(ANY_VISIT_ID);
    }

    @Test
    void shouldCreateVisit() {
        final BigDecimal initialAvailableCredit = customer.getAvailableCredit();

        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.of(customer));
        given(visitMapper.createVisitDTOToVisit(createVisitDTO)).willReturn(visit);
        given(visitRepository.save(visit)).willReturn(visit);

        final long result = visitService.createVisit(createVisitDTO);

        assertThat(result, is(ANY_VISIT_ID));
        assertThat(visit.getCustomer(), is(customer));
        assertThat(visit.getVisitTotal(), is(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE)));
        assertThat(customer.getAvailableCredit(), is(initialAvailableCredit.subtract(visit.getVisitTotal())));

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
        then(visitMapper).should(times(1)).createVisitDTOToVisit(createVisitDTO);
        then(visitRepository).should(times(1)).save(visit);
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenCreatingVisitWithNonExistentCustomer() {
        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> visitService.createVisit(createVisitDTO));

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
    }

    @Test
    void shouldThrowExceededCreditLimitExceptionWhenCreatingVisitThatExceedsCreditLimit() {
        customer.setCreditLimit(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE - 1));

        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.of(customer));

        assertThrows(ExceededCreditLimitException.class, () -> visitService.createVisit(createVisitDTO));

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
    }

    @Test
    void shouldThrowInsufficientCreditExceptionWhenCreatingVisitThatExceedsAvailableCredit() {
        customer.setAvailableCredit(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE - 1));

        given(customerRepository.findById(ANY_CUSTOMER_ID)).willReturn(Optional.of(customer));

        assertThrows(InsufficientCreditException.class, () -> visitService.createVisit(createVisitDTO));

        then(customerRepository).should(times(1)).findById(ANY_CUSTOMER_ID);
    }

    @Test
    void shouldUpdateVisit() {
        final BigDecimal initialAvailableCredit = customer.getAvailableCredit();
        visit.setCustomer(customer);
        visit.setNet(ANOTHER_NET);
        visit.setVisitTotal(ANY_VISIT_TOTAL);

        given(visitRepository.findById(ANY_VISIT_ID)).willReturn(Optional.of(visit));
        willDoNothing().given(visitMapper).updateVisitDTOToVisit(visit, updateVisitDTO);

        visitService.updateVisit(ANY_VISIT_ID, updateVisitDTO);

        assertThat(visit.getCustomer(), is(customer));
        assertThat(visit.getVisitTotal(), is(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE)));
        assertThat(customer.getAvailableCredit(),
                is(initialAvailableCredit.add(ANY_VISIT_TOTAL).subtract(visit.getVisitTotal())));

        then(visitRepository).should(times(1)).findById(ANY_VISIT_ID);
        then(visitMapper).should(times(1)).updateVisitDTOToVisit(visit, updateVisitDTO);
    }

    @Test
    void shouldThrowVisitNotFoundExceptionWhenUpdatingNonExistentVisit() {
        given(visitRepository.findById(ANY_VISIT_ID)).willReturn(Optional.empty());

        assertThrows(VisitNotFoundException.class, () -> visitService.updateVisit(ANY_VISIT_ID, updateVisitDTO));

        then(visitRepository).should(times(1)).findById(ANY_VISIT_ID);
    }

    @Test
    void shouldThrowExceededCreditLimitExceptionWhenUpdatingVisitThatExceedsCreditLimit() {
        customer.setCreditLimit(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE - 1));
        visit.setCustomer(customer);

        given(visitRepository.findById(ANY_VISIT_ID)).willReturn(Optional.of(visit));

        assertThrows(ExceededCreditLimitException.class, () -> visitService.updateVisit(ANY_VISIT_ID, updateVisitDTO));

        then(visitRepository).should(times(1)).findById(ANY_VISIT_ID);
    }

    @Test
    void shouldThrowInsufficientCreditExceptionWhenUpdatingVisitThatExceedsAvailableCredit() {
        customer.setAvailableCredit(BigDecimal.valueOf(ANY_NET * ANY_VISITS_PERCENTAGE - 1));
        visit.setCustomer(customer);

        given(visitRepository.findById(ANY_VISIT_ID)).willReturn(Optional.of(visit));

        assertThrows(InsufficientCreditException.class, () -> visitService.updateVisit(ANY_VISIT_ID, updateVisitDTO));

        then(visitRepository).should(times(1)).findById(ANY_VISIT_ID);
    }

    @Test
    void shouldDeleteVisit() {
        final BigDecimal initialAvailableCredit = customer.getAvailableCredit();

        visit.setCustomer(customer);
        visit.setVisitTotal(ANY_VISIT_TOTAL);

        given(visitRepository.findById(ANY_VISIT_ID)).willReturn(Optional.of(visit));
        willDoNothing().given(visitRepository).delete(visit);

        visitService.deleteVisit(ANY_VISIT_ID);

        assertThat(customer.getAvailableCredit(), is(initialAvailableCredit.add(visit.getVisitTotal())));

        then(visitRepository).should(times(1)).findById(ANY_VISIT_ID);
        then(visitRepository).should(times(1)).delete(any(Visit.class));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistentVisit() {
        given(visitRepository.findById(ANY_VISIT_ID)).willReturn(Optional.empty());

        visitService.deleteVisit(ANY_VISIT_ID);

        then(visitRepository).should(times(1)).findById(ANY_VISIT_ID);
        then(visitRepository).should(times(0)).delete(any(Visit.class));
    }

}