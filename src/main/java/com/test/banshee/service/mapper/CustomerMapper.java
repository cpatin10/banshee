package com.test.banshee.service.mapper;

import com.test.banshee.domain.Customer;
import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {VisitMapper.class, LocationMapper.class})
public abstract class CustomerMapper {

    @Mapping(source = "city", target = "location")
    public abstract GetCustomerDTO customerToGetCustomerDTO(Customer customer);

    @Mapping(source = "location", target = "city")
    public abstract Customer createCustomerDTOToCustomer(CreateCustomerDTO dto);

    @Mapping(source = "location", target = "city")
    public abstract void updateCustomerDTOToCustomer(@MappingTarget final Customer customer, UpdateCustomerDTO dto);

}
