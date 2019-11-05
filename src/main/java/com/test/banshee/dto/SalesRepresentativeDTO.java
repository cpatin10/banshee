package com.test.banshee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesRepresentativeDTO {

    private String fullName;

    private String identificationNumber;

}
