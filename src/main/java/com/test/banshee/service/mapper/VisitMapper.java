package com.test.banshee.service.mapper;

import com.test.banshee.domain.Visit;
import com.test.banshee.dto.visit.CreateVisitDTO;
import com.test.banshee.dto.visit.GetVisitDTO;
import com.test.banshee.dto.visit.UpdateVisitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = SalesRepresentativeMapper.class)
public abstract class VisitMapper {

    @Mapping(source = "salesRepresentativeIdNumber", target = "salesRepresentative")
    public abstract Visit createVisitDTOToVisit(CreateVisitDTO dto);

    @Mapping(source = "salesRepresentativeIdNumber", target = "salesRepresentative")
    public abstract void updateVisitDTOToVisit(@MappingTarget Visit visit, UpdateVisitDTO dto);

    public abstract GetVisitDTO visitToGetVisitDTO(Visit visit);

}
