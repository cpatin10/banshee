package com.test.banshee.service.mapper;

import com.test.banshee.domain.SalesRepresentative;
import com.test.banshee.dto.SalesRepresentativeDTO;
import com.test.banshee.exception.notfound.SalesRepresentativeNotFoundException;
import com.test.banshee.repository.SalesRepresentativeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.test.banshee.constants.SalesRepresentativeTestConstants.ANY_SALES_REPRESENTATIVE_FULL_NAME;
import static com.test.banshee.constants.SalesRepresentativeTestConstants.ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SalesRepresentativeMapperTest {

    @Mock
    private SalesRepresentativeRepository salesRepresentativeRepository;

    @InjectMocks
    private SalesRepresentativeMapper salesRepresentativeMapper = new SalesRepresentativeMapperImpl();

    private SalesRepresentative salesRepresentative;

    @BeforeEach
    void setUp() {
        salesRepresentative = new SalesRepresentative();
        salesRepresentative.setFullName(ANY_SALES_REPRESENTATIVE_FULL_NAME);
        salesRepresentative.setIdentificationNumber(ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER);
    }

    @Test
    void shouldMapSalesRepresentativeToSalesRepresentativeDTO() {
        final SalesRepresentativeDTO result =
                salesRepresentativeMapper.salesRepresentativeToSalesRepresentativeDTO(salesRepresentative);

        assertThat(result.getIdentificationNumber(), is(salesRepresentative.getIdentificationNumber()));
        assertThat(result.getFullName(), is(salesRepresentative.getFullName()));
    }

    @Test
    void shouldGetSalesRepresentative() {
        given(salesRepresentativeRepository.findByIdentificationNumber(ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER))
                .willReturn(Optional.of(salesRepresentative));

        final SalesRepresentative result =
                salesRepresentativeMapper.getSalesRepresentative(ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER);

        assertThat(result, is(salesRepresentative));

        then(salesRepresentativeRepository).should(times(1))
                .findByIdentificationNumber(ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER);
    }

    @Test
    void shouldThrowSalesRepresentativeNotFoundExceptionWhenGettingNonExistentSalesRepresentative() {
        given(salesRepresentativeRepository.findByIdentificationNumber(ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER))
                .willReturn(Optional.empty());

        assertThrows(SalesRepresentativeNotFoundException.class,
                () -> salesRepresentativeMapper.getSalesRepresentative(ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER));

        then(salesRepresentativeRepository).should(times(1))
                .findByIdentificationNumber(ANY_SALES_REPRESENTATIVE_IDENTIFICATION_NUMBER);
    }

}