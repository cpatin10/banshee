package com.test.banshee.service.mapper;

import com.test.banshee.domain.City;
import com.test.banshee.dto.location.GetLocationDTO;
import com.test.banshee.dto.location.LocationDTO;
import com.test.banshee.exception.notfound.LocationNotFoundException;
import com.test.banshee.repository.CityRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {

    @Autowired
    private CityRepository cityRepository;

    public City locationDTOToCity(final LocationDTO locationDTO) {
        return cityRepository.findByNameAndStateNameAndStateCountryName(
                locationDTO.getCity(), locationDTO.getState(), locationDTO.getCountry())
                .orElseThrow(() -> new LocationNotFoundException(
                        locationDTO.getCity(), locationDTO.getState(), locationDTO.getCountry()));
    }

    public GetLocationDTO cityToGetLocationDTO(final City city) {
        final GetLocationDTO locationDTO = new GetLocationDTO();
        Optional.ofNullable(city)
                .ifPresent(c -> {
                    locationDTO.setCity(c.getName());
                    Optional.ofNullable(c.getState()).ifPresent(state -> {
                        locationDTO.setState(state.getName());
                        Optional.ofNullable(state.getCountry())
                                .ifPresent(country -> locationDTO.setCountry(country.getName()));
                    });
                });
        return locationDTO;
    }

}
