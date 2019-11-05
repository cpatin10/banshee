package com.test.banshee.service.mapper;

import com.test.banshee.domain.SalesRepresentative;
import com.test.banshee.dto.SalesRepresentativeDTO;
import com.test.banshee.exception.notfound.SalesRepresentativeNotFoundException;
import com.test.banshee.repository.SalesRepresentativeRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SalesRepresentativeMapper {

    @Autowired
    private SalesRepresentativeRepository salesRepresentativeRepository;

    public abstract SalesRepresentativeDTO salesRepresentativeToSalesRepresentativeDTO(
            SalesRepresentative salesRepresentative);

    public SalesRepresentative getSalesRepresentative(final String identificationNumber) {
        return salesRepresentativeRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new SalesRepresentativeNotFoundException(identificationNumber));
    }

}
