package com.test.banshee.dto.customer;

import com.test.banshee.dto.location.LocationDTO;
import com.test.banshee.validator.Nit;
import com.test.banshee.validator.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDTO {

    @NotBlank
    @Nit
    private String nit;

    @NotBlank
    private String fullName;

    @NotBlank
    private String address;

    @NotBlank
    @Phone
    private String phone;

    @NotNull
    private LocationDTO location;

    @NotNull
    @Min(0)
    private BigDecimal creditLimit;

    @NotNull
    @Min(0)
    private BigDecimal availableCredit;

    @NotNull
    @Min(0)
    private BigDecimal visitPercentage;

}
