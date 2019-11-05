package com.test.banshee.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

}
