package com.test.banshee.dto.customer;

import com.test.banshee.dto.location.GetLocationDTO;
import com.test.banshee.dto.visit.GetVisitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerDTO {

    private long id;

    private String nit;

    private String fullName;

    private String address;

    private String phone;

    private GetLocationDTO location;

    private BigDecimal creditLimit;

    private BigDecimal availableCredit;

    private Double visitPercentage;

    private List<GetVisitDTO> visits;

}
