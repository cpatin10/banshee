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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final CustomerRepository customerRepository;

    private final VisitMapper visitMapper;

    @Override
    @Transactional
    public Long createVisit(final CreateVisitDTO createVisitDTO) {
        final Customer customer = getCustomer(createVisitDTO.getCustomerId());
        final BigDecimal visitTotal = getVisitTotal(createVisitDTO.getNet(), customer);
        final Visit mappedVisit = visitMapper.createVisitDTOToVisit(createVisitDTO);
        mappedVisit.setCustomer(customer);
        mappedVisit.setVisitTotal(visitTotal);
        customer.setAvailableCredit(customer.getAvailableCredit().subtract(visitTotal));
        return visitRepository.save(mappedVisit).getId();
    }

    @Override
    @Transactional
    public void updateVisit(final long visitId, final UpdateVisitDTO updateVisitDTO) {
        final Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException(visitId));
        final Customer customer = visit.getCustomer();
        final BigDecimal newVisitTotal = getVisitTotal(updateVisitDTO.getNet(), customer);
        customer.setAvailableCredit(customer.getAvailableCredit().add(visit.getVisitTotal()).subtract(newVisitTotal));
        visitMapper.updateVisitDTOToVisit(visit, updateVisitDTO);
        visit.setVisitTotal(newVisitTotal);
    }

    @Override
    @Transactional
    public void deleteVisit(final long visitId) {
        visitRepository.findById(visitId)
                .ifPresent(visit -> {
                    final Customer customer = visit.getCustomer();
                    customer.setAvailableCredit(customer.getAvailableCredit().add(visit.getVisitTotal()));
                    visitRepository.delete(visit);
                });
    }

    private Customer getCustomer(final long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private BigDecimal getVisitTotal(final long net, final Customer customer) {
        final BigDecimal visitTotal = BigDecimal.valueOf(customer.getVisitPercentage() * net);
        if (visitTotal.compareTo(customer.getCreditLimit()) > 0) {
            throw new ExceededCreditLimitException(visitTotal, customer.getCreditLimit());
        } else if (visitTotal.compareTo(customer.getAvailableCredit()) > 0) {
            throw new InsufficientCreditException(visitTotal, customer.getCreditLimit());
        }
        return visitTotal;
    }

}
