package com.test.banshee.service.mapper;

import com.test.banshee.domain.City;
import com.test.banshee.domain.Country;
import com.test.banshee.domain.State;
import com.test.banshee.dto.location.GetLocationDTO;
import com.test.banshee.dto.location.LocationDTO;
import com.test.banshee.exception.notfound.LocationNotFoundException;
import com.test.banshee.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.test.banshee.constants.LocationTestConstants.ANY_CITY;
import static com.test.banshee.constants.LocationTestConstants.ANY_COUNTRY;
import static com.test.banshee.constants.LocationTestConstants.ANY_STATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith({MockitoExtension.class})
class LocationMapperTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private LocationMapper locationMapper = new LocationMapperImpl();

    private LocationDTO locationDTO;

    private City city;

    @BeforeEach
    void setUp() {
        final Country country = new Country();
        country.setName(ANY_COUNTRY);

        final State state = new State();
        state.setName(ANY_STATE);
        state.setCountry(country);

        city = new City();
        city.setName(ANY_CITY);
        city.setState(state);

        locationDTO = LocationDTO.builder()
                .city(ANY_CITY)
                .country(ANY_COUNTRY)
                .state(ANY_STATE)
                .build();
    }

    @Test
    void shouldMapLocationDTOToCity() {
        given(cityRepository.findByNameAndStateNameAndStateCountryName(ANY_CITY, ANY_STATE, ANY_COUNTRY))
                .willReturn(Optional.of(city));

        final City result = locationMapper.locationDTOToCity(locationDTO);

        assertThat(result, is(city));

        then(cityRepository).should(times(1))
                .findByNameAndStateNameAndStateCountryName(ANY_CITY, ANY_STATE, ANY_COUNTRY);
    }

    @Test
    void shouldThrowLocationNotFoundExceptionWhenLocationDTODoesNotMatchAnyCity() {
        given(cityRepository.findByNameAndStateNameAndStateCountryName(ANY_CITY, ANY_STATE, ANY_COUNTRY))
                .willReturn(Optional.empty());

        assertThrows(LocationNotFoundException.class, () -> locationMapper.locationDTOToCity(locationDTO));

        then(cityRepository).should(times(1))
                .findByNameAndStateNameAndStateCountryName(ANY_CITY, ANY_STATE, ANY_COUNTRY);
    }

    @Test
    void shouldMapCityToGetLocationDTO() {
        final GetLocationDTO getLocationDTO = locationMapper.cityToGetLocationDTO(city);

        assertThat(getLocationDTO.getCity(), is(city.getName()));
        assertThat(getLocationDTO.getState(), is(city.getState().getName()));
        assertThat(getLocationDTO.getCountry(), is(city.getState().getCountry().getName()));
    }

}