package com.test.banshee.service.mapper;

import com.test.banshee.domain.City;
import com.test.banshee.domain.Customer;
import com.test.banshee.domain.Visit;
import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.CustomerSummary;
import com.test.banshee.dto.customer.CustomerSummaryDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import com.test.banshee.dto.location.GetLocationDTO;
import com.test.banshee.dto.location.LocationDTO;
import com.test.banshee.dto.visit.GetVisitDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.test.banshee.constants.CustomerTestConstants.ANY_ADDRESS;
import static com.test.banshee.constants.CustomerTestConstants.ANY_AVAILABLE_CREDIT;
import static com.test.banshee.constants.CustomerTestConstants.ANY_CREDIT_LIMIT;
import static com.test.banshee.constants.CustomerTestConstants.ANY_CUSTOMER_FULL_NAME;
import static com.test.banshee.constants.CustomerTestConstants.ANY_CUSTOMER_ID;
import static com.test.banshee.constants.CustomerTestConstants.ANY_NIT;
import static com.test.banshee.constants.CustomerTestConstants.ANY_PHONE;
import static com.test.banshee.constants.CustomerTestConstants.ANY_UPDATED_ADDRESS;
import static com.test.banshee.constants.CustomerTestConstants.ANY_UPDATED_AVAILABLE_CREDIT;
import static com.test.banshee.constants.CustomerTestConstants.ANY_UPDATED_CREDIT_LIMIT;
import static com.test.banshee.constants.CustomerTestConstants.ANY_UPDATED_CUSTOMER_FULL_NAME;
import static com.test.banshee.constants.CustomerTestConstants.ANY_UPDATED_NIT;
import static com.test.banshee.constants.CustomerTestConstants.ANY_UPDATED_PHONE;
import static com.test.banshee.constants.CustomerTestConstants.ANY_UPDATED_VISIT_PERCENTAGE;
import static com.test.banshee.constants.CustomerTestConstants.ANY_VISIT_PERCENTAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

    @Mock
    private VisitMapper visitMapper;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private CustomerMapper customerMapper = new CustomerMapperImpl();

    private Customer customer;

    private City city;

    private Visit visit;

    @BeforeEach
    void setUp() {
        city = new City();
        visit = new Visit();

        customer = new Customer();
        customer.setId(ANY_CUSTOMER_ID);
        customer.setNit(ANY_NIT);
        customer.setFullName(ANY_CUSTOMER_FULL_NAME);
        customer.setAddress(ANY_ADDRESS);
        customer.setPhone(ANY_PHONE);
        customer.setCity(city);
        customer.setAvailableCredit(ANY_AVAILABLE_CREDIT);
        customer.setCreditLimit(ANY_CREDIT_LIMIT);
        customer.setVisitPercentage(ANY_VISIT_PERCENTAGE);
        customer.getVisits().add(visit);
        customer.getVisits().add(visit);
    }

    @Test
    void shouldMapCustomerToGetCustomerDTO() {
        final GetLocationDTO getLocationDTO = GetLocationDTO.builder().build();
        final GetVisitDTO getVisitDTO = GetVisitDTO.builder().build();

        given(locationMapper.cityToGetLocationDTO(city)).willReturn(getLocationDTO);
        given(visitMapper.visitToGetVisitDTO(visit)).willReturn(getVisitDTO);

        final GetCustomerDTO result = customerMapper.customerToGetCustomerDTO(customer);

        assertThat(result.getId(), is(customer.getId()));
        assertThat(result.getNit(), is(customer.getNit()));
        assertThat(result.getFullName(), is(customer.getFullName()));
        assertThat(result.getAddress(), is(customer.getAddress()));
        assertThat(result.getPhone(), is(customer.getPhone()));
        assertThat(result.getAvailableCredit(), is(customer.getAvailableCredit()));
        assertThat(result.getCreditLimit(), is(customer.getCreditLimit()));
        assertThat(result.getVisitPercentage(), is(customer.getVisitPercentage()));
        assertThat(result.getLocation(), is(getLocationDTO));
        assertThat(result.getVisits().size(), is(2));
        assertTrue(result.getVisits().stream().allMatch(v -> v.equals(getVisitDTO)));

        then(locationMapper).should(times(1)).cityToGetLocationDTO(city);
        then(visitMapper).should(times(2)).visitToGetVisitDTO(visit);
    }

    @Test
    void shouldMapCustomerSummaryToCustomerSummaryDTO() {
        final CustomerSummary customerSummary = Mockito.mock(CustomerSummary.class);

        given(customerSummary.getId()).willReturn(ANY_CUSTOMER_ID);
        given(customerSummary.getNit()).willReturn(ANY_NIT);
        given(customerSummary.getFullName()).willReturn(ANY_CUSTOMER_FULL_NAME);

        final CustomerSummaryDTO result = customerMapper.customerSummaryToCustomerSummaryDTO(customerSummary);

        assertThat(result.getId(), is(ANY_CUSTOMER_ID));
        assertThat(result.getNit(), is(ANY_NIT));
        assertThat(result.getFullName(), is(ANY_CUSTOMER_FULL_NAME));

        then(customerSummary).should(times(1)).getId();
        then(customerSummary).should(times(1)).getNit();
        then(customerSummary).should(times(1)).getFullName();
    }

    @Test
    void shouldMapCreateCustomerDTOToCustomer() {
        final LocationDTO locationDTO = LocationDTO.builder().build();
        final City city = new City();
        final CreateCustomerDTO createCustomerDTO = CreateCustomerDTO.builder()
                .nit(ANY_NIT)
                .address(ANY_ADDRESS)
                .availableCredit(ANY_AVAILABLE_CREDIT)
                .creditLimit(ANY_CREDIT_LIMIT)
                .fullName(ANY_CUSTOMER_FULL_NAME)
                .phone(ANY_PHONE)
                .visitPercentage(ANY_VISIT_PERCENTAGE)
                .location(locationDTO)
                .build();

        given(locationMapper.locationDTOToCity(locationDTO)).willReturn(city);

        final Customer result = customerMapper.createCustomerDTOToCustomer(createCustomerDTO);

        assertThat(result.getId(), is(nullValue()));
        assertThat(result.getNit(), is(createCustomerDTO.getNit()));
        assertThat(result.getFullName(), is(createCustomerDTO.getFullName()));
        assertThat(result.getAddress(), is(createCustomerDTO.getAddress()));
        assertThat(result.getPhone(), is(createCustomerDTO.getPhone()));
        assertThat(result.getAvailableCredit(), is(createCustomerDTO.getAvailableCredit()));
        assertThat(result.getCreditLimit(), is(createCustomerDTO.getCreditLimit()));
        assertThat(result.getVisitPercentage(), is(createCustomerDTO.getVisitPercentage()));
        assertThat(result.getCity(), is(city));

        then(locationMapper).should(times(1)).locationDTOToCity(locationDTO);
    }

    @Test
    void shouldMapUpdateCustomerDTOToCustomer() {
        final LocationDTO locationDTO = LocationDTO.builder().build();
        final UpdateCustomerDTO updateCustomerDTO = UpdateCustomerDTO.builder()
                .nit(ANY_UPDATED_NIT)
                .address(ANY_UPDATED_ADDRESS)
                .availableCredit(ANY_UPDATED_AVAILABLE_CREDIT)
                .creditLimit(ANY_UPDATED_CREDIT_LIMIT)
                .fullName(ANY_UPDATED_CUSTOMER_FULL_NAME)
                .phone(ANY_UPDATED_PHONE)
                .visitPercentage(ANY_UPDATED_VISIT_PERCENTAGE)
                .location(locationDTO)
                .build();

        given(locationMapper.locationDTOToCity(locationDTO)).willReturn(city);

        customerMapper.updateCustomerDTOToCustomer(customer, updateCustomerDTO);

        assertThat(customer.getId(), is(ANY_CUSTOMER_ID));
        assertThat(customer.getNit(), is(updateCustomerDTO.getNit()));
        assertThat(customer.getFullName(), is(updateCustomerDTO.getFullName()));
        assertThat(customer.getAddress(), is(updateCustomerDTO.getAddress()));
        assertThat(customer.getPhone(), is(updateCustomerDTO.getPhone()));
        assertThat(customer.getAvailableCredit(), is(updateCustomerDTO.getAvailableCredit()));
        assertThat(customer.getCreditLimit(), is(updateCustomerDTO.getCreditLimit()));
        assertThat(customer.getVisitPercentage(), is(updateCustomerDTO.getVisitPercentage()));
        assertThat(customer.getCity(), is(city));
        assertThat(customer.getVisits().size(), is(2));
        assertTrue(customer.getVisits().stream().allMatch(v -> v.equals(visit)));

        then(locationMapper).should(times(1)).locationDTOToCity(locationDTO);
    }

}