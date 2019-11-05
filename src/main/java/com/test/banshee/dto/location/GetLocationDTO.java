package com.test.banshee.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLocationDTO {

    private String city;

    private String state;

    private String country;

}
